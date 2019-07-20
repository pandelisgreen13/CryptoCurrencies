package  com.pchasapis.cryptocurrency.mvp.presenter.base

import com.pchasapis.cryptocurrency.mvp.interactor.base.MVPInteractor
import com.pchasapis.cryptocurrency.mvp.view.base.MVPView
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.net.UnknownHostException

open class BasePresenter<out V : MVPView, out I : MVPInteractor>(view: V, interactor: I) :
        MVPPresenter<V, I> {

    private var viewRef: WeakReference<V>? = null
    private var interactor: I? = interactor


    protected var job = SupervisorJob()
    protected val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    protected val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    protected val uiScope = CoroutineScope(Dispatchers.Main + job)

    init {
        viewRef = WeakReference(view)
    }

    override fun getView(): V? {
        return viewRef?.get()
    }

    override fun getInteractor(): I? {
        return interactor
    }

    override fun isViewAttached(): Boolean {
        return viewRef != null && viewRef!!.get() != null && viewRef!!.get()!!.isAttached()
    }

    override fun detach() {
        uiScope.coroutineContext.cancelChildren()
        viewRef?.clear()
        interactor?.detach()
    }

    fun isNetworkError(throwable: Throwable): Boolean {
        when (throwable) {
            is UnknownHostException -> {
                return true
            }
        }
        return false
    }

    fun onErrorThrowable(throwable: Throwable, isShowEmptyView: Boolean = false) {
        getView()?.hideLoader()
        if (isNetworkError(throwable)) {
            getView()?.showNoInternetError()
        } else {
            getView()?.showGenericError()
        }
        if (isShowEmptyView)
            getView()?.showEmpty()
    }
}