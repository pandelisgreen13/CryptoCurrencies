package com.pchasapis.cryptocurrency.mvp.interactor.productDetails

import com.google.gson.Gson
import com.pchasapis.cryptocurrency.models.common.DataResult
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.CryptoListResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.LiveDataResponse
import com.pchasapis.cryptocurrency.models.parsers.crypto.liveData.Rate
import com.pchasapis.cryptocurrency.mvp.interactor.base.BaseInteractor
import com.pchasapis.cryptocurrency.network.client.CryptoClient
import org.json.JSONObject
import timber.log.Timber


class ProductDetailsInteractorImpl(private var cryptoClient: CryptoClient) : BaseInteractor(), ProductDetailsInteractor {


}