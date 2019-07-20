package com.pchasapis.cryptocurrency.mvp.presenter.home

import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.list.Crypto
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.BasePresenter
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePresenterImpl(mainView: HomeView, homeInteractor: HomeInteractor) : BasePresenter<HomeView, HomeInteractor>(mainView, homeInteractor), HomePresenter {

    private var rateDataModelList = arrayListOf<RateDataModel>()
    private var isTyping: Boolean = false

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
            responseRate?.data?.let { rateDateModelList ->
                val responseCrypto = withContext(bgDispatcher) { getInteractor()?.onRetrieveCrypotList() }
                responseCrypto?.data?.let { cryptoList ->
                    getView()?.hideLoader()
                    rateDataModelList = handleList(rateDateModelList, cryptoList)
                    getView()?.onRetrieveLiveRates(rateDataModelList)
                } ?: run {
                    responseCrypto?.throwable?.let { onErrorThrowable(it) }
                    getView()?.showEmpty()
                }
            } ?: run {
                responseRate?.throwable?.let { onErrorThrowable(it) }
                getView()?.showEmpty()
            }
        }
    }

    override fun searchForCrypto(queryText: String?) {
        if (!isViewAttached() || queryText.isNullOrEmpty()) {
            return
        }
        this.isTyping = !isTyping
        getView()?.updateSearchButton(isTyping)

        if (!isTyping) {
            getView()?.onRetrieveLiveRates(rateDataModelList)
            return
        }

        val queryList = rateDataModelList.filter { it.title.toLowerCase() == queryText.toLowerCase() }
        getView()?.onRetrieveLiveRates(queryList)
    }

    override fun showList(isTyping: Boolean) {
        if (!isViewAttached()) {
            return
        }
        this.isTyping = isTyping
        getView()?.onRetrieveLiveRates(rateDataModelList)
    }

    private fun handleList(rateDateModelList: List<RateDataModel>, cryptoList: List<Crypto>): ArrayList<RateDataModel> {
        return ArrayList(rateDateModelList.map { rateDataModel ->
            return@map RateDataModel(title = rateDataModel.title, rate = rateDataModel.rate, target = rateDataModel.target, crypto = foundCryptoById(rateDataModel.title, cryptoList))
        })
    }

    private fun foundCryptoById(title: String, cryptoList: List<Crypto>): Crypto? {
        return cryptoList.find { it.symbol == title }
    }
}