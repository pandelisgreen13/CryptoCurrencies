package com.pchasapis.cryptocurrency.mvp.interactor.productDetails

import com.pchasapis.cryptocurrency.mvp.interactor.base.BaseInteractor
import com.pchasapis.cryptocurrency.network.client.CryptoClient


class ProductDetailsInteractorImpl(private var cryptoClient: CryptoClient) : BaseInteractor(), ProductDetailsInteractor {


}