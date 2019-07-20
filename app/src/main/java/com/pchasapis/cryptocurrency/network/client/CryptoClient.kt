package com.pchasapis.cryptocurrency.network.client

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pchasapis.cryptocurrency.common.Definitions
import com.pchasapis.cryptocurrency.common.DefinitionsApi
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.CryptoListResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.LiveDataResponse
import com.pchasapis.cryptocurrency.network.api.CryptoApi
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CryptoClient {

    private var cryptoApi: CryptoApi

    init {
        cryptoApi = getRetrofit().create(CryptoApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(DefinitionsApi.DOMAIN)
                .client(getOkHttpClient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .build()

    fun getLiveRatesAsync(currency: String): Deferred<LiveDataResponse> {
        return cryptoApi.getLiveRatesAsync(Definitions.ACCESS_KEY, Definitions.EXPAND, currency)
    }

    fun getCryptoListAsync(): Deferred<CryptoListResponse> {
        return cryptoApi.getCryptoListAsync(Definitions.ACCESS_KEY)
    }


}