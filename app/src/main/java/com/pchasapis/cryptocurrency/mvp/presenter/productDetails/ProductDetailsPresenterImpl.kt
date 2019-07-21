package com.pchasapis.cryptocurrency.mvp.presenter.productDetails

import com.github.mikephil.charting.data.Entry
import com.pchasapis.cryptocurrency.common.extensions.DateFormatterExtentions.DATE_FORMAT
import com.pchasapis.cryptocurrency.common.extensions.getFormattedDate
import com.pchasapis.cryptocurrency.common.extensions.getFormattedStringFromDate
import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.models.parsers.crypto.timeFrame.TimeFrame
import com.pchasapis.cryptocurrency.mvp.interactor.productDetails.ProductDetailsInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.BasePresenter
import com.pchasapis.cryptocurrency.mvp.view.productDetails.ProductDetailsView
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

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
            val response = withContext(bgDispatcher) {
                getInteractor()?.onRetrieveTimeFrame(rateDataModel.crypto?.symbol!!,
                        getPreviousMonth(),
                        getFormattedDate(System.currentTimeMillis(), DATE_FORMAT),
                        rateDataModel.target)
            }
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

    private fun getPreviousMonth(): String {
        val month = Calendar.getInstance()
        month.add(Calendar.MONTH, -1)
        return getFormattedStringFromDate(month.time, DATE_FORMAT)
    }
}