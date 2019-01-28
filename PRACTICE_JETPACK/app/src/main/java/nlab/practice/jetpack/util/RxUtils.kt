package nlab.practice.jetpack.util

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Doohyun
 */
object RxUtils {

    fun createLazyDisposables(): CompositeDisposable {
        val result : CompositeDisposable by lazy { CompositeDisposable() }

        return result
    }

}