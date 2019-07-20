package com.pchasapis.cryptocurrency.models.parsers.crypto.liveData

import com.google.gson.annotations.SerializedName
import com.pchasapis.cryptocurrency.models.parsers.common.CommonResponse

data class LiveDataResponse(@SerializedName("terms") val terms: String?,
                            @SerializedName("rates") val liveDataRates: LiveDataRates?,
                            @SerializedName("privacy") val privacy: String?,
                            @SerializedName("timestamp") val timestamp: Int? ,
                            @SerializedName("target") val target: String?) : CommonResponse()