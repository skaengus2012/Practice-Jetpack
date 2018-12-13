package nlab.practice.jetpack.ui.home

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.di.android.InjectableAndroidViewModel
import nlab.practice.jetpack.ui.common.MainContainerViewModel
import nlab.practice.jetpack.util.recyclerview.anko.AnkoViewBindingItem
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeViewModel(application: Application): InjectableAndroidViewModel(application), MainContainerViewModel {

    @Inject
    lateinit var disposables: CompositeDisposable

    private val homeHeaderViewModel = HomeHeaderViewModel(injector)

    init {
        injector.inject(this)
    }

    override fun onCleared() {
        stopHeaderTimer()
        disposables.clear()
    }

    override fun getHeader(): AnkoViewBindingItem? = homeHeaderViewModel

    fun startHeaderTimer() {
        homeHeaderViewModel.startTimer()
    }

    fun stopHeaderTimer() {
        homeHeaderViewModel.stopTimer()
    }
}