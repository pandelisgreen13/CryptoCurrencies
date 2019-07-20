package com.pchasapis.cryptocurrency.mvp.interactor.home

import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor

interface HomeInteractor : MVPInteractor {
    suspend fun onRetrieveRates(currency: String): DataResult<List<RateDataModel>>

    suspend fun onRetrieveCryptoList(): DataResult<List<Crypto>>
}