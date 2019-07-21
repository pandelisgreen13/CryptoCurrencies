package com.pchasapis.cryptocurrency.mvp.view.productDetails

import com.github.mikephil.charting.data.Entry
import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView

interface ProductDetailsView : MVPView {
    fun showCryptoModel(rateDataModel: RateDataModel?)
    fun showTimeFrame(timeFrameList: ArrayList<Entry>)
}
