package nlab.practice.jetpack.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author Doohyun
 * @since 2019. 01. 24
 */
class ToastHelper(private val _context: Context) {

    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, message, duration).show()
    }

    fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(_context, message, duration).show()
    }
}