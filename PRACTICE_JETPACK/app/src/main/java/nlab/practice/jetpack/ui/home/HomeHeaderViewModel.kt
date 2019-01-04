package nlab.practice.jetpack.ui.home

import Njava.util.time.TimeBuilder
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.createLazyCompositeDisposable
import nlab.practice.jetpack.util.recyclerview.databinding.DataBindingItemViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Home 상단 프로필에 대한 이벤트 정의
 *
 * @author Doohyun
 */
class HomeHeaderViewModel @Inject constructor(
        resourceProvider: ResourceProvider,
        private val androidScheduler: Scheduler): DataBindingItemViewModel() {

    private val _timerDisposables = createLazyCompositeDisposable()

    private val _dateFormat: CharSequence = resourceProvider.getString(R.string.home_time_format)

    @Bindable
    var currentTimeString: String = getCurrentTimeDateFormat()
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentTimeString)
        }

    override fun getLayoutRes(): Int = R.layout.view_home_header

    fun startTimer() {
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .repeat()
                .map { getCurrentTimeDateFormat() }
                .observeOn(androidScheduler)
                .filter { it != currentTimeString}
                .doOnNext { currentTimeString = it }
                .subscribe()
                .addTo(_timerDisposables)
    }

    fun stopTimer() = _timerDisposables.clear()

    private fun getCurrentTimeDateFormat(): String = TimeBuilder.Create()
            .getStringFormat(_dateFormat.toString())
            .blockingGet("")
}