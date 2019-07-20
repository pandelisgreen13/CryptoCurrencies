package com.pchasapis.cryptocurrency.mvp.interactor.home

import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.LiveDataResponse
import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor

interface HomeInteractor : MVPInteractor {
    suspend fun onRetrieveRates(): DataResult<List<RateDataModel>>

    suspend fun onRetrieveCrypotList(): DataResult<List<Crypto>>
}