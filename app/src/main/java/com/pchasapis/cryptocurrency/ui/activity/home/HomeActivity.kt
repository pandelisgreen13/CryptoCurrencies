package com.pchasapis.cryptocurrency.ui.activity.home

import android.os.Bundle
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.common.application.CryptoAplication
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.mvp.interactor.home.HomeInteractorImpl
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenter
import com.pchasapis.cryptocurrency.mvp.presenter.home.HomePresenterImpl
import com.pchasapis.cryptocurrency.mvp.view.home.HomeView
import com.pchasapis.cryptocurrency.ui.activity.base.BaseMVPActivity
import timber.log.Timber

class HomeActivity : BaseMVPActivity<HomePresenter>(), HomeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenterImpl(this, HomeInteractorImpl(CryptoAplication.get()?.cryptoClient!!))
        initLayout()
        presenter?.getRates()
    }

    private fun initLayout() {

    }

    override fun onRetrieveLiveRates(liveDataResponse: List<RateDataModel>) {
        Timber.d("")
    }

}
