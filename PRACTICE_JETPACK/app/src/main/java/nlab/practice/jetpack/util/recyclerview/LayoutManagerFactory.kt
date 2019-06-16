package nlab.practice.jetpack.util.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class LayoutManagerFactory(private val _context: Context) {

    fun createGridLayoutManager(spanCount: Int) = GridLayoutManager(_context, spanCount)

    fun createLinearLayoutManager(
            @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
            , reverseLayout: Boolean = false) = LinearLayoutManager(_context, orientation, reverseLayout)
}
