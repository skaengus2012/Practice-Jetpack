package nlab.practice.jetpack.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * @author Doohyun
 */
class SnackBarHelper(private val _viewSupplier: ()-> View?) {

    private fun getView(): View? = _viewSupplier()

    fun showSnackBar(message: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) {
        getView()?.run { Snackbar.make(this, message, duration).show() }
    }

    fun showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        getView()?.run { Snackbar.make(this, message, duration).show() }
    }
}