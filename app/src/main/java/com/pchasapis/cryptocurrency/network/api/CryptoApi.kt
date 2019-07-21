package com.pchasapis.cryptocurrency.network.api

import com.pchasapis.cryptocurrency.models.parsers.crypto.list.CryptoListResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.LiveDataResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrameResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("/live")
    fun getLiveRatesAsync(@Query("access_key") accessKey: String,
                          @Query("expand") expand: String,
                          @Query("target")  target: String): Deferred<LiveDataResponse>


    @GET("/list")
    fun getCryptoListAsync(@Query("access_key") accessKey: String): Deferred<CryptoListResponse>


    @GET("/timeframe")
    fun getTimeFrameAsync(@Query("access_key") accessKey: String,
                          @Query("start_date") start_date: String,
                          @Query("end_date") end_date: String,
                          @Query("symbols") symbols: String): Deferred<TimeFrameResponse>

}