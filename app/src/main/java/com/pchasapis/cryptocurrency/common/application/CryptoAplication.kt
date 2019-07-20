package com.pchasapis.cryptocurrency.common.application

import android.app.Application
import com.pchasapis.cryptocurrency.BuildConfig
import com.pchasapis.cryptocurrency.network.client.CryptoClient
import timber.log.Timber
import java.lang.ref.WeakReference


class CryptoAplication : Application() {

    companion object {
        private var instance: WeakReference<CryptoAplication>? = null

        @JvmStatic
        fun get(): CryptoAplication? {
            return instance?.get()
        }
    }

    val cryptoClient: CryptoClient by lazy {
        return@lazy CryptoClient()
    }

    override fun onCreate() {
        super.onCreate()
        instance = WeakReference(this)
        initTimberLogging()
    }

    private fun initTimberLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}