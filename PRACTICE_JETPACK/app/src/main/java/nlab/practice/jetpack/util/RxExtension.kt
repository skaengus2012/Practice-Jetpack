package nlab.practice.jetpack.util

import io.reactivex.disposables.CompositeDisposable

/**
 * @author Doohyun
 */

fun createLazyCompositeDisposable(): CompositeDisposable {
    val result : CompositeDisposable by lazy { CompositeDisposable() }

    return result
}