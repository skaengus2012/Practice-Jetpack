package nlab.practice.jetpack.util

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import nlab.practice.jetpack.R

/**
 * @author Doohyun
 */
class SnackBarHelper(
        private val _snackBarHeight: Int = 0,
        private val _resourceProvider: ResourceProvider,
        private val _viewSupplier: ()-> View?) {

    private fun getView(): View? = _viewSupplier()

    fun showSnackBar(message: CharSequence, duration: Int = Snackbar.LENGTH_SHORT) {
        getView()?.run { showSnackBar(Snackbar.make(this, message, duration)) }
    }

    fun showSnackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        getView()?.run { showSnackBar(Snackbar.make(this, message, duration)) }
    }

    private fun showSnackBar(snackBar: Snackbar) {
        decorate(snackBar)

        snackBar.show()
    }

    private fun decorate(snackBar: Snackbar) {
        val originSnapBarSize = _resourceProvider.getDimensionPixelSize(R.dimen.origin_snap_bar_height)

        if (_snackBarHeight != 0) {
            snackBar.view.minimumHeight = _snackBarHeight

            snackBar.view.findViewById<TextView>(R.id.snackbar_text)?.run {
                // 텍스트 중앙처리
                (this.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = (originSnapBarSize - _snackBarHeight)
            }
        }
    }

}