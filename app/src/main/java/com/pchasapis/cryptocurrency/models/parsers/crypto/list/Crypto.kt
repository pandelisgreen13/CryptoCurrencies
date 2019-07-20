package com.pchasapis.cryptocurrency.models.parsers.crypto.list

import com.google.gson.annotations.SerializedName

data class Crypto(@SerializedName("icon_url") val iconUrl: String? = null,
                  @SerializedName("symbol") val symbol: String? = null,
                  @SerializedName("name_full") val nameFull: String? = null,
                  @SerializedName("name") val name: String? = null,
                  @SerializedName("max_supply") val maxSupply: String? = null)