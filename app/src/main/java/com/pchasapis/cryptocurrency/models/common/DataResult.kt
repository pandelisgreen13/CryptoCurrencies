package com.pchasapis.cryptocurrency.models.common

data class DataResult<out T>(
        val data: T? = null,
        val throwable: Throwable? = null
)