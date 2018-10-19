package nlab.practice.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.ui.layout.ActivityMainUI
import org.jetbrains.anko.setContentView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val _text : ObservableField<String> = ObservableField()
    private val _disposable : CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _text.set("Hello World")
        ActivityMainUI(_text).setContentView(this)

        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { _text.set("Data changed for ObservableField") }
                .subscribe()
                .addTo(_disposable)
    }
}
