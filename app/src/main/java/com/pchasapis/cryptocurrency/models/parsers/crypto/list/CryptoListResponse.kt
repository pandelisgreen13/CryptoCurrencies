package com.pchasapis.cryptocurrency.models.parsers.crypto.list

import com.google.gson.annotations.SerializedName
import com.pchasapis.cryptocurrency.models.parsers.common.CommonResponse

data class CryptoListResponse(@SerializedName("crypto") val cryptoList: CryptoList? = null) : CommonResponse()