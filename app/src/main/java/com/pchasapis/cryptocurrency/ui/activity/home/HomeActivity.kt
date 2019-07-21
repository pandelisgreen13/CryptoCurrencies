package com.pchasapis.cryptocurrency.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import closeSoftKeyboard
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.common.application.CryptoApplication
import com.pchasapis.cryptocurrency.models.objects.rate.RateDataModel
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractorImpl
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenter
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenterImpl
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import com.pchasapis.cryptocurrency.ui.activity.base.BaseMVPActivity
import com.pchasapis.cryptocurrency.ui.adapter.HomeRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import android.text.TextWatcher
import com.pchasapis.cryptocurrency.common.BUNDLE
import com.pchasapis.cryptocurrency.ui.activity.productDetails.ProductDetailActivity
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import com.pchasapis.cryptocurrency.models.objects.event.PollingEvent
import org.greenrobot.eventbus.EventBus


class HomeActivity : BaseMVPActivity<HomePresenter>(), HomeView {

    private var homeRecyclerViewAdapter: HomeRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initLayout()
        presenter = HomePresenterImpl(this, HomeInteractorImpl(CryptoApplication.get()?.cryptoClient!!))
        presenter?.getRates()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: PollingEvent) {
        presenter?.getRates()
    }

    override fun showLoader() {
        super.showLoader()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun hideLoader() {
        super.hideLoader()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun updateCurrencyButton(isEuro: Boolean) {
        actionButtonImageView.setImageResource(if (isEuro) R.drawable.ic_euro else R.drawable.ic_dollar)
    }

    override fun onRetrieveLiveRates(liveDataList: List<RateDataModel>) {
        homeRecyclerViewAdapter?.setCryptoList(liveDataList)
    }

    private fun initLayout() {
        backButtonImageView.visibility = View.INVISIBLE
        actionButtonImageView.visibility = View.VISIBLE
        toolbarTitleTextView.text = getString(R.string.home_toolbar_title)

        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter { rateDataModel ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(BUNDLE.CRYPTO_DETAILS, rateDataModel)
            startActivity(intent)
        }
        homeRecyclerView.adapter = homeRecyclerViewAdapter

        actionButtonImageView.setOnClickListener {
            searchEditText.clearFocus()
            closeSoftKeyboard(this@HomeActivity)
            presenter?.getListWithDifferentCurrency()
        }

        swipeRefreshLayout.setOnRefreshListener { presenter?.getRates() }
        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(charSequence: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if (charSequence.isEmpty()) {
                    presenter?.showList()
                    searchEditText.clearFocus()
                    closeSoftKeyboard(this@HomeActivity)
                    return
                }
                presenter?.searchForCrypto(searchEditText.text.toString())
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })
    }

}
