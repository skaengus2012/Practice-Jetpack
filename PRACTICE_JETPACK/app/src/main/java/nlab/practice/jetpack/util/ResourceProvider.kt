package nlab.practice.jetpack.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Resource 제공자 정의
 *
 * @author Doohyun
 * @since 2018. 12. 18
 */
class ResourceProvider(private val _context: Context) {
    fun getColor(@ColorRes colorRes: Int) : Int = ContextCompat.getColor(_context, colorRes)

    fun getString(@StringRes stringRes: Int): CharSequence = _context.getString(stringRes)

    fun getString(@StringRes stringFormatRes: Int, vararg arguments: Any?): String{
        val format = getString(stringFormatRes)

        return String.format(format.toString(), *arguments)
    }

    fun getDimensionPixelSize(@DimenRes resource: Int): Int = _context.resources.getDimensionPixelSize(resource)
}
