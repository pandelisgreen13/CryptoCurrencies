package com.pchasapis.cryptocurrency.mvp.view.productDetails

import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView

interface ProductDetailsView : MVPView {
    fun showCryptoModel(rateDataModel: RateDataModel?)
}
