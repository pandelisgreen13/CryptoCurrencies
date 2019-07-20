package com.pchasapis.cryptocurrency.models.objects

import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.Rate

class RateDataModel(var title: String, var rate: Rate, var target: String, var crypto: Crypto? = null)