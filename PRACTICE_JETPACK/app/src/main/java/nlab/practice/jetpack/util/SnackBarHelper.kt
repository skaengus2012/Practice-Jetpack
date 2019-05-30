package nlab.practice.jetpack.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * @author Doohyun
 */
class SnackBarHelper(viewSupplier: ()-> View?) {

    private val _view: View? by lazyPublic(viewSupplier)

    fun showSnackBar(message: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) {
        _view?.run { Snackbar.make(this, message, duration).show() }
    }

    fun showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        _view?.run { Snackbar.make(this, message, duration).show() }
    }
}