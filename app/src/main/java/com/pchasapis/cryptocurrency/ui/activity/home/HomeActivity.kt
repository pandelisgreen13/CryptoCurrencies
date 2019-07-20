package com.pchasapis.cryptocurrency.ui.activity.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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

class HomeActivity : BaseMVPActivity<HomePresenter>(), HomeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initLayout()
        presenter = HomePresenterImpl(this, HomeInteractorImpl(CryptoAplication.get()?.cryptoClient!!))
        presenter?.getRates()
    }

    private fun initLayout() {
        backButtonImageView.visibility = View.INVISIBLE
        toolbarTitleTextView.text = getString(R.string.home_toolbar_title)
    }

    override fun onRetrieveLiveRates(liveDataList: List<RateDataModel>) {
        homeRecyclerView.layoutManager = LinearLayoutManager(this)
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.adapter = HomeRecyclerViewAdapter(liveDataList, {})
    }

}
