package com.pchasapis.cryptocurrency.models.parsers.crypto.liveData

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rate(@SerializedName("change_pct") val changePct: Double? = null,
                @SerializedName("high") val high: Double? = null,
                @SerializedName("rate") val rate: Double? = null,
                @SerializedName("low") val low: Double? = null,
                @SerializedName("change") val change: Double? = null) : Parcelable