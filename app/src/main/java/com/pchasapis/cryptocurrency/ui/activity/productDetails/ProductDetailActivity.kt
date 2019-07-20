package com.pchasapis.cryptocurrency.ui.activity.productDetails

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.common.BUNDLE
import com.pchasapis.cryptocurrency.common.application.CryptoAplication
import com.pchasapis.cryptocurrency.common.extensions.getAmountWithDecimalAndCurrencySymbolEnd
import com.pchasapis.cryptocurrency.common.extensions.loadUrl
import com.pchasapis.cryptocurrency.models.objects.RateDataModel
import com.pchasapis.cryptocurrency.mvp.interactor.productDetails.ProductDetailsInteractorImpl
import com.pchasapis.cryptocurrency.mvp.presenter.productDetails.ProductDetailsPresenter
import com.pchasapis.cryptocurrency.mvp.presenter.productDetails.ProductDetailsPresenterImpl
import com.pchasapis.cryptocurrency.mvp.view.productDetails.ProductDetailsView
import com.pchasapis.cryptocurrency.ui.activity.base.BaseMVPActivity
import kotlinx.android.synthetic.main.layout_crypto_details.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ProductDetailActivity : BaseMVPActivity<ProductDetailsPresenter>(), ProductDetailsView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        presenter = ProductDetailsPresenterImpl(this,
                intent?.extras?.getParcelable<RateDataModel>(BUNDLE.CRYPTO_DETAILS),
                ProductDetailsInteractorImpl(CryptoAplication.get()?.cryptoClient!!))
        initLayout()
    }

    @SuppressLint("SetTextI18n")
    override fun showCryptoModel(rateDataModel: RateDataModel?) {
        cryptoNameTextView.text = "${rateDataModel?.title}/${rateDataModel?.target}"
        cryptoRateTextView.text = rateDataModel?.rate?.rate?.getAmountWithDecimalAndCurrencySymbolEnd(this, rateDataModel.target)
                ?: "-"
        cryptoHighTextView.text = rateDataModel?.rate?.high?.getAmountWithDecimalAndCurrencySymbolEnd(this, rateDataModel.target)
                ?: "-"
        cryptoLowTextView.text = rateDataModel?.rate?.low?.getAmountWithDecimalAndCurrencySymbolEnd(this, rateDataModel.target)
                ?: "-"

        cryptoImageView.loadUrl(rateDataModel?.crypto?.iconUrl)
    }

    private fun initLayout() {
        backButtonImageView.visibility = View.VISIBLE
        backButtonImageView.setOnClickListener { onBackPressed() }
        presenter?.getCryptoModel()
    }
}
