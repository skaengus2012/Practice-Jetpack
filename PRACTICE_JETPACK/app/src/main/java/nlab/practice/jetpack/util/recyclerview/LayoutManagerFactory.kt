package nlab.practice.jetpack.util.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class LayoutManagerFactory(private val _context: Context) {

    fun createGridLayoutManager(spanCount: Int): GridLayoutManager = GridLayoutManager(_context, spanCount)

    fun createGridLayoutManager(
            spanCount: Int,
            @RecyclerView.Orientation orientation: Int,
            reverseLayout: Boolean) : GridLayoutManager {
        return GridLayoutManager(_context, spanCount, orientation, reverseLayout)
    }
}