package com.pchasapis.cryptocurrency.mvp.view.base

interface MVPView {
    fun isAttached(): Boolean
    fun showLoader()
    fun hideLoader()
    fun showEmpty()
    fun hideEmpty()
    fun showError(error: String)
    fun showNoInternetError()
    fun showGenericError()
}