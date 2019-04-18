package nlab.practice.jetpack.util.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class LayoutManagerFactory(private val _context: Context) {

    fun createGridLayoutManager(spanCount: Int) = GridLayoutManager(_context, spanCount)
}
