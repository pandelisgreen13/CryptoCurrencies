package com.pchasapis.cryptocurrency.mvp.interactor.productDetails

import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrameResponse
import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor

interface ProductDetailsInteractor : MVPInteractor {

    suspend fun onRetrieveTimeFrame(symbol: String): DataResult<TimeFrameResponse>

}