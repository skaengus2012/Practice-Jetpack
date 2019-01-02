package nlab.practice.jetpack.util

import android.content.Context
import androidx.annotation.DimenRes
import androidx.annotation.StringRes

/**
 * Resource 제공자 정의
 *
 * @author Doohyun
 * @since 2018. 12. 18
 */
interface ResourceProvider {
    fun getString(@StringRes stringRes: Int): CharSequence
    fun getDimensionPixelSize(@DimenRes resource: Int): Int
}

class ResourceProviderImpl(private val _context: Context): ResourceProvider {
    override fun getString(@StringRes stringRes: Int): CharSequence = _context.getString(stringRes)
    override fun getDimensionPixelSize(@DimenRes resource: Int): Int = _context.resources.getDimensionPixelSize(resource)
}
