package com.pchasapis.cryptocurrency.mvp.presenter.productDetails

import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractor
import com.pchasapis.cryptocurrency.mvp.interactor.productDetails.ProductDetailsInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.BasePresenter
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import com.pchasapis.cryptocurrency.mvp.view.productDetails.ProductDetailsView
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsPresenterImpl(productDetailsView: ProductDetailsView,
                                  private val rateDataModel: RateDataModel?,
                                  productInteractor: ProductDetailsInteractor)
    : BasePresenter<ProductDetailsView, ProductDetailsInteractor>(productDetailsView, productInteractor), ProductDetailsPresenter {

    override fun getCryptoModel() {
        if(!isViewAttached()){
            return
        }
        getView()?.showCryptoModel(rateDataModel)
    }
}