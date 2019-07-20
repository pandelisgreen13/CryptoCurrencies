package com.pchasapis.cryptocurrency.common.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.pchasapis.cryptocurrency.BuildConfig
import com.pchasapis.cryptocurrency.network.client.CryptoClient
import com.pchasapis.cryptocurrency.service.polling.PollingService
import timber.log.Timber
import java.lang.ref.WeakReference


class CryptoApplication : Application() {

    companion object {
        private var instance: WeakReference<CryptoApplication>? = null

        @JvmStatic
        fun get(): CryptoApplication? {
            return instance?.get()
        }
    }

    val cryptoClient: CryptoClient by lazy {
        return@lazy CryptoClient()
    }

    var isInForeground: Boolean = false
        private set
        @Synchronized get

    private var pollingService: PollingService? = null
    private val pollingServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            pollingService = null
        }

        @SuppressLint("SetTextI18n")
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val pollingServiceBinder = binder as PollingService.PollingServiceBinder?
            pollingService = pollingServiceBinder?.service
            pollingService?.startPeriodicUpdates()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = WeakReference(this)
        initTimberLogging()
        initLifeCycleListener()
    }

    private fun initTimberLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun startPeriodicUpdates() {
        PollingService.startService(this)
        PollingService.connectToService(this, pollingServiceConnection)
    }

    private fun stopPeriodicUpdates() {
        pollingService?.stopPeriodicUpdates()
        PollingService.disconnectToService(this, pollingServiceConnection)
        PollingService.stopService(this)
    }

    private fun initLifeCycleListener() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onMoveToForeground() {
                Timber.d("CryptoApplication > LifecycleObserver > onMoveToForeground")
                isInForeground = true
                startPeriodicUpdates()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onMoveToBackground() {
                Timber.d("CryptoApplication > LifecycleObserver > onMoveToBackground")
                isInForeground = false
                stopPeriodicUpdates()
            }
        })
    }
}