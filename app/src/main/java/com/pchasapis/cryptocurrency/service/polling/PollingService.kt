package com.pchasapis.cryptocurrency.service.polling

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.pchasapis.cryptocurrency.models.objects.event.PollingEvent
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class PollingService : Service() {

    companion object {
        @JvmStatic
        fun startService(context: Context) {
            val serviceIntent = Intent(context, PollingService::class.java)
            context.startService(serviceIntent)
        }

        @JvmStatic
        fun stopService(context: Context) {
            val serviceIntent = Intent(context, PollingService::class.java)
            context.stopService(serviceIntent)
        }

        @JvmStatic
        fun connectToService(context: Context, serviceConnection: ServiceConnection) {
            val serviceIntent = Intent(context, PollingService::class.java)
            context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        @JvmStatic
        fun disconnectToService(context: Context, serviceConnection: ServiceConnection) {
            context.unbindService(serviceConnection)
        }
    }

    private var job = SupervisorJob()
    private val bgScope = CoroutineScope(Dispatchers.IO + job)
    private val binder = PollingServiceBinder()

    private var isRunning: Boolean = false
        @Synchronized get() {
            return field
        }
        @Synchronized set(value) {
            field = value
        }

    inner class PollingServiceBinder : Binder() {
        val service: PollingService
            get() = this@PollingService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRunning()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopRunning()
        stopSelf()
    }

    @Synchronized
    fun startPeriodicUpdates() {
        isRunning = true
        bgScope.launch {
            while (isRunning) {
                try {
                    fetchCrypto()
                } catch (e: Exception) {
                    Timber.d(e)
                } finally {
                    delay(60 * 10000) //10 minute delay
                }
            }
        }
    }

    @Synchronized
    fun stopPeriodicUpdates() {
        isRunning = false
    }

    private fun stopRunning() {
        stopPeriodicUpdates()
        bgScope.coroutineContext.cancelChildren()
    }

    private fun fetchCrypto() {
        Timber.d("PollingService -> New Event")
        EventBus.getDefault().postSticky(PollingEvent())
    }
}