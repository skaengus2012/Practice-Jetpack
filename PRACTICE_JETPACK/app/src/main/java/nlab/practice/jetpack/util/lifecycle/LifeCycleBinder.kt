package nlab.practice.jetpack.util.lifecycle

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

/**
 * LifeCycle 중계기
 *
 * @author Doohyun
 */
class LifeCycleBinder<T>(val disposable: CompositeDisposable) {

    val subject: BehaviorSubject<T> = BehaviorSubject.create()

    inline fun bindUntil(code: T, crossinline action: () -> Unit) {
        subject.filter { it == code }
                .doOnNext { action() }
                .subscribe()
                .addTo(disposable)
    }

    fun apply(event: T) = subject.onNext(event)
}