package nlab.practice.jetpack.ui.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

/**
 * @author Doohyun
 * @since 2018. 11. 16
 */
class MainTestViewModel(application: Application): AndroidViewModel(application) {

    val observableTest = ObservableField<String>("Hello")

    private val _disposable = CompositeDisposable()

    init {
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { observableTest.set("Data changed for ObservableField") }
                .subscribe()
                .addTo(_disposable)
    }

    override fun onCleared() {
        _disposable.clear()
    }
}