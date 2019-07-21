package com.pchasapis.cryptocurrency.mvp.interactor.productDetails

import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrameResponse
import com.pchasapis.cryptocurrency.mvp.interactor.base.BaseInteractor
import com.pchasapis.cryptocurrency.network.client.CryptoClient
import timber.log.Timber


class ProductDetailsInteractorImpl(private var cryptoClient: CryptoClient) : BaseInteractor(), ProductDetailsInteractor {


    override suspend fun onRetrieveTimeFrame(symbol: String, startDate: String, endDate: String, target: String): DataResult<TimeFrameResponse> {
        return try {
            val response = cryptoClient.getTimeFrameAsync(symbol, startDate, endDate, target).await()
            DataResult(response)
        } catch (t: Throwable) {
            Timber.d(t)
            DataResult(throwable = t)
        }
    }

}