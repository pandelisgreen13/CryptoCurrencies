package com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame

import com.google.gson.annotations.SerializedName
import com.pchasapis.cryptocurrency.models.parsers.common.CommonResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.Rate

data class TimeFrameResponse(

        @SerializedName("success")
        var success: Boolean? = false,

        @SerializedName("end_date")
        var endDate: String? = null,

        @SerializedName("timeframe")
        var timeFrame: Boolean? = null,

        @SerializedName("terms")
        var terms: String? = null,

        @SerializedName("rates")
        var rates: List<TimeFrame>? = null,

        @SerializedName("privacy")
        var privacy: String? = null,

        @SerializedName("start_date")
        var startDate: String? = null,

        @SerializedName("target")
        var target: String? = null
)