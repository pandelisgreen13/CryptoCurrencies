package com.pchasapis.cryptocurrency.models.objects

import android.os.Parcelable
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.Rate
import kotlinx.android.parcel.Parcelize

@Parcelize
class RateDataModel(var title: String, var rate: Rate, var target: String, var crypto: Crypto? = null):Parcelable