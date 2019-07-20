package com.pchasapis.cryptocurrency.models.parsers.crypto.liveData

import com.google.gson.annotations.SerializedName

data class Rate(@SerializedName("change_pct") val changePct: Double? = null,
                @SerializedName("high") val high: Double? = null,
                @SerializedName("rate") val rate: Double? = null,
                @SerializedName("low") val low: Double? = null,
                @SerializedName("change") val change: Double? = null)