package com.pchasapis.cryptocurrency.mvp.presenter.productDetails

import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractor
import com.pchasapis.cryptocurrency.mvp.interactor.productDetails.ProductDetailsInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.MVPPresenter
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import com.pchasapis.cryptocurrency.mvp.view.productDetails.ProductDetailsView

interface ProductDetailsPresenter : MVPPresenter<ProductDetailsView, ProductDetailsInteractor> {


    fun getCryptoModel()
}