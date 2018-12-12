package nlab.practice.jetpack.ui.ankomvvm

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.android.InjectableAndroidViewModel
import nlab.practice.jetpack.util.string
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstViewModel(application: Application) : InjectableAndroidViewModel(application) {

    @Inject lateinit var disposable: CompositeDisposable

    @Inject lateinit var context: Context

    val message : ObservableField<String> = ObservableField()

    init {
        injector.inject(this)

        context.string(R.string.anko_first_message).run { message.set(this) }
    }

    override fun onCleared() {
        disposable.clear()
    }

    fun changeTextDelayTime(message: String, second: Long = 0L) {
        Observable.timer(second, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { this.message.set(message) }
                .subscribe()
                .addTo(disposable)
    }
}