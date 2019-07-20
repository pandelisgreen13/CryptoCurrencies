package com.pchasapis.cryptocurrency.ui.activity.base

import android.annotation.SuppressLint
import android.view.View
import com.pchasapis.cryptocurrency.R
import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor
import com.pchasapis.cryptocurrency.mvp.presenter.base.MVPPresenter
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView
import kotlinx.android.synthetic.main.layout_empty.*
import kotlinx.android.synthetic.main.layout_loading.*

@SuppressLint("Registered")
open class BaseMVPActivity<T : MVPPresenter<MVPView, MVPInteractor>> : BaseActivity(), MVPView {

    protected var presenter: T? = null

    override fun isAttached(): Boolean {
        return !isFinishing
    }

    override fun showLoader() {
        hideEmpty()
        loadingView?.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loadingView?.visibility = View.GONE
    }

    override fun hideEmpty() {
        emptyView?.visibility = View.GONE
    }

    override fun showEmpty() {
        hideLoader()
        emptyView?.visibility = View.VISIBLE
    }

    override fun showError(error: String) {
        showErrorDialog(error, closeListener  = {dialog->
            dialog.dismiss()
        })
    }

    override fun showGenericError() {
        showErrorDialog(getString(R.string.generic_error), closeListener = {dialog->
            dialog.dismiss()
        })
    }

    override fun showNoInternetError() {
        showErrorDialog(getString(R.string.no_internet), closeListener = {dialog->
            dialog.dismiss()
        })
    }


}