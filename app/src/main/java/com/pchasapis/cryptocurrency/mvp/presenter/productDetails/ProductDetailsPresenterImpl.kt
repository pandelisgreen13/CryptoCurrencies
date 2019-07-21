package com.pchasapis.cryptocurrency.mvp.presenter.productDetails

import com.github.mikephil.charting.data.Entry
import com.pchasapis.cryptocurrency.common.extensions.getFormattedDate
import com.pchasapis.cryptocurrency.common.extensions.parseStringDateToMillis
import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrame
import com.pchasapis.cryptocurrency.mvp.interactor.productDetails.ProductDetailsInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.BasePresenter
import com.pchasapis.cryptocurrency.mvp.view.productDetails.ProductDetailsView
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsPresenterImpl(productDetailsView: ProductDetailsView,
                                  private val rateDataModel: RateDataModel?,
                                  productInteractor: ProductDetailsInteractor)
    : BasePresenter<ProductDetailsView, ProductDetailsInteractor>(productDetailsView, productInteractor), ProductDetailsPresenter {

    override fun getCryptoModel() {
        if (!isViewAttached()) {
            return
        }
        getView()?.showCryptoModel(rateDataModel)
    }

    override fun getTimeFrame() {
        if (!isViewAttached() || rateDataModel?.crypto?.symbol == null) {
            return
        }
        uiScope.launch {
            getView()?.showLoader()
            val response = withContext(bgDispatcher) { getInteractor()?.onRetrieveTimeFrame(rateDataModel.crypto?.symbol!!) }
            if (!isViewAttached()) {
                return@launch
            }
            getView()?.hideLoader()
            response?.data?.let { timeFrameList ->
                getView()?.showTimeFrame(getPieChartDataEntries(timeFrameList.rates))
            } ?: run {
                response?.throwable?.let { onErrorThrowable(it) }
                getView()?.showEmpty()
            }
        }
    }

    private fun getPieChartDataEntries(ratesList: List<TimeFrame>?): ArrayList<Entry> {
        if (ratesList == null || ratesList.isEmpty()) {
            return arrayListOf()
        }
        val entries = ArrayList<Entry>()
        for (i in 0 until ratesList.size) {
            if (ratesList[i].rate == null) {
                continue
            }
            // turn your data into Entry objects
            entries.add(Entry(i.toFloat(), ratesList[i].rate?.toFloat() ?: 0F))
        }
        return entries
    }
}