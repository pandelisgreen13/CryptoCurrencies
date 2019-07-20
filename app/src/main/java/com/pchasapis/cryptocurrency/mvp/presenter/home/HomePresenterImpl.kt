package com.pchasapis.cryptocurrency.mvp.presenter.home

import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.BasePresenter
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePresenterImpl(mainView: HomeView, homeInteractor: HomeInteractor) : BasePresenter<HomeView, HomeInteractor>(mainView, homeInteractor), HomePresenter {

    override fun getRates() {
        if (!isViewAttached()) {
            return
        }
        uiScope.launch {
            getView()?.showLoader()
            val responseRate = withContext(bgDispatcher) { getInteractor()?.onRetrieveRates() }
            if (!isViewAttached()) {
                return@launch
            }
            getView()?.hideLoader()
            responseRate?.data?.let { rateDateModelList ->
                val responseCrypto = withContext(bgDispatcher) { getInteractor()?.onRetrieveCrypotList() }
                responseCrypto?.data?.let { cryptoList ->
                    getView()?.onRetrieveLiveRates(handleList(rateDateModelList, cryptoList))
                } ?: run {
                    getView()?.showEmpty()
                }
            } ?: run {
                getView()?.showEmpty()
            }
        }
    }

    private fun handleList(rateDateModelList: List<RateDataModel>, cryptoList: List<Crypto>): List<RateDataModel> {
        return ArrayList(rateDateModelList.map { rateDataModel ->
            return@map RateDataModel(title = rateDataModel.title, rate = rateDataModel.rate, target = rateDataModel.target, crypto = foundCryptoById(rateDataModel.title, cryptoList))

        })
    }

    private fun foundCryptoById(title: String, cryptoList: List<Crypto>): Crypto? {
        return cryptoList.find { it.symbol == title }
    }
}