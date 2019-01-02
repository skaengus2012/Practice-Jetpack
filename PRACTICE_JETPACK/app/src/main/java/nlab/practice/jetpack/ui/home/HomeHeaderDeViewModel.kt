package nlab.practice.jetpack.ui.home

import Njava.util.time.TimeBuilder
import android.content.Context
import android.view.ViewGroup
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.util.recyclerview_de.anko.AnkoViewBindingItem
import nlab.practice.jetpack.util.string
import org.jetbrains.anko.AnkoComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeHeaderDeViewModel(injector: ViewModelInjectComponent) : AnkoViewBindingItem() {

    @Inject lateinit var context: Context

    private val _timerDisposables : CompositeDisposable by lazy { CompositeDisposable() }
    private val _viewComponent = HomeHeaderUI(this)
    private val _dateFormat: String

    @Bindable
    var currentTimeString: String = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.currentTimeString)
    }

    init {
        injector.inject(this)

        _dateFormat = context.string(R.string.home_time_format)
        currentTimeString = getCurrentTimeDateFormat()
    }

    override fun getViewComponent(): AnkoComponent<ViewGroup> = _viewComponent

    fun startTimer() {
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .repeat()
                .map { getCurrentTimeDateFormat() }
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it != currentTimeString}
                .doOnNext { currentTimeString = it }
                .subscribe()
                .addTo(_timerDisposables)
    }

    fun stopTimer() {
        _timerDisposables.clear()
    }

    private fun getCurrentTimeDateFormat(): String = TimeBuilder.Create()
            .getStringFormat(_dateFormat)
            .blockingGet("")
}