package com.pchasapis.cryptocurrency.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import closeSoftKeyboard
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.common.application.CryptoAplication
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractorImpl
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenter
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenterImpl
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import com.pchasapis.cryptocurrency.ui.activity.base.BaseMVPActivity
import com.pchasapis.cryptocurrency.ui.adapter.HomeRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import android.text.TextWatcher
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pchasapis.cryptocurrency.common.BUNDLE
import com.pchasapis.cryptocurrency.ui.activity.productDetails.ProductDetailActivity


class HomeActivity : BaseMVPActivity<HomePresenter>(), HomeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initLayout()
        presenter = HomePresenterImpl(this, HomeInteractorImpl(CryptoAplication.get()?.cryptoClient!!))
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

    override fun showEmpty() {
        super.showEmpty()
    }

    override fun onRetrieveLiveRates(liveDataList: List<RateDataModel>) {
        closeSoftKeyboard(this)
        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.adapter = HomeRecyclerViewAdapter(liveDataList) { rateDataModel ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra(BUNDLE.CRYPTO_DETAILS, rateDataModel)
            startActivity(intent)
        }
    }

    override fun updateSearchButton(isTyping: Boolean) {
        val searchIcon: Int
        when {
            isTyping -> searchIcon = R.drawable.ic_close
            else -> {
                searchIcon = R.drawable.ic_search
                searchEditText.setText("")
                searchEditText.clearFocus()
            }
        }
        searchImageView.setImageResource(searchIcon)
    }

    private fun initLayout() {
        backButtonImageView.visibility = View.INVISIBLE
        toolbarTitleTextView.text = getString(R.string.home_toolbar_title)
        searchImageView.setOnClickListener {
            presenter?.searchForCrypto(searchEditText.text.toString())
        }

        swipeRefreshLayout.setOnRefreshListener { presenter?.getRates() }

        searchEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if (cs.isEmpty()) {
                    presenter?.showList(false)
                    searchEditText.clearFocus()
                    searchImageView.setImageResource(R.drawable.ic_search)
                }
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })
    }

}
