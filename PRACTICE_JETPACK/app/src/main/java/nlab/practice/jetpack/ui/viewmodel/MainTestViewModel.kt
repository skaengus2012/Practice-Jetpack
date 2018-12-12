package nlab.practice.jetpack.ui.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.di.android.InjectableAndroidViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 11. 16
 */
class MainTestViewModel(application: Application): InjectableAndroidViewModel(application) {

    val observableTest = ObservableField<String>("Hello")

    @Inject lateinit var disposable: CompositeDisposable

    init {
        injector.inject(this)

        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { observableTest.set("Data changed for ObservableField") }
                .subscribe()
                .addTo(disposable)
    }

    override fun onCleared() {
        disposable.clear()
    }
}