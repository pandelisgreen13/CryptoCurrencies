package com.pchasapis.cryptocurrency.mvp.view.home

import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView

interface HomeView : MVPView {
    fun onRetrieveLiveRates(liveDataList: List<RateDataModel>)
    fun updateSearchButton(isTyping: Boolean)
    fun updateCurrencyButton(isEuro: Boolean)
}