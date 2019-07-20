package com.pchasapis.cryptocurrency.mvp.presenter.base

import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView

interface MVPPresenter<out V : MVPView, out I : MVPInteractor> {
    fun detach()
    fun getView(): V?
    fun getInteractor(): I?
    fun isViewAttached() : Boolean
}