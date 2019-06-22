package nlab.practice.jetpack.util

import android.view.View

/**
 * @author Doohyun
 */
class ViewSupplier<T: View>(private val viewSupplier: () -> T) {
    fun get(): T = viewSupplier()
}