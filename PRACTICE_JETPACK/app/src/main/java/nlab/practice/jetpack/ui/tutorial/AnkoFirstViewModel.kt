package nlab.practice.jetpack.ui.tutorial

import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstViewModel @Inject constructor(
        private val _disposable: CompositeDisposable,
        private val _androidScheduler: Scheduler,
        ankoFirstDataBundle: AnkoFirstDataBundle,
        lifeCycleBinder: ActivityLifeCycleBinder,
        resourceProvider: ResourceProvider) {

    val message : ObservableField<String> = ObservableField()

    init {
        (ankoFirstDataBundle.message?: resourceProvider.getString(R.string.anko_first_message)).run {
            message.set(toString())
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            ankoFirstDataBundle.message = null
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_DESTROY) {
            message.get()?.run { ankoFirstDataBundle.message = this }
        }
    }


    fun changeTextDelayTime(message: String, second: Long = 0L) {
        Observable.timer(second, TimeUnit.SECONDS)
                .observeOn(_androidScheduler)
                .doOnNext { this.message.set(message) }
                .subscribe()
                .addTo(_disposable)
    }
}