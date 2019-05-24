package nlab.practice.jetpack.util

import android.view.View

/**
 * @author Doohyun
 */
class ViewSupplier<T: View>(private val _viewSupplier: () -> T) {
    fun get(): T = _viewSupplier()
}