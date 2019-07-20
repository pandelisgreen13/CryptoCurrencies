package com.pchasapis.cryptocurrency.mvp.view.home

import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView

interface HomeView : MVPView {
    fun onRetrieveLiveRates(liveDataList: List<RateDataModel>)
    fun updateSearchButton(isTyping: Boolean)
}