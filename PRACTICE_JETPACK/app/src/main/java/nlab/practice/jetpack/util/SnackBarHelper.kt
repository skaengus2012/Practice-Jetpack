package nlab.practice.jetpack.util

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import nlab.practice.jetpack.R

/**
 * @author Doohyun
 */
class SnackBarHelper(private val _resourceProvider: ResourceProvider, private val _viewSupplier: ()-> View?) {

    private val _targetView: View?
    get() = _viewSupplier()

    fun showSnackBar(
            @StringRes message: Int,
            duration: Int = Snackbar.LENGTH_SHORT,
            @StringRes actionMessage: Int? = null,
            actionBehavior: (() -> Unit)? = null) = _targetView?.run {
        Snackbar.make(this, message, duration)
                .decorate()
                .setActionInternal(actionMessage, actionBehavior)
                .show()
    }

    private fun Snackbar.setActionInternal(@StringRes actionMessage: Int?, actionBehavior: (() -> Unit)?): Snackbar {
        if ((actionMessage != null) && (actionBehavior != null)) {
            setAction(actionMessage) { actionBehavior() }
        }

        return this
    }

    private fun Snackbar.decorate(): Snackbar {
        decorateTextView(view.findViewById(R.id.snackbar_text))

        return this
    }

    private fun decorateTextView(view: TextView?) = view?.run {
        minHeight = _resourceProvider.getDimensionPixelSize(R.dimen.main_bottom_navigation_height)
        gravity = Gravity.CENTER_VERTICAL
        setPadding(0,0,0,0)
    }
}