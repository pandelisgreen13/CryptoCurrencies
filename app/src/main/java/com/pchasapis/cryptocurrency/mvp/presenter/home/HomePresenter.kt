package com.pchasapis.cryptocurrency.mvp.presenter.home

import com.pchasapis.cryptocurrency.common.Definitions
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.MVPPresenter
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import java.util.*

interface HomePresenter : MVPPresenter<HomeView, HomeInteractor> {
    fun getRates()
    fun searchForCrypto(queryText: String?)
    fun showList()
    fun getListWithDifferentCurrency()

}