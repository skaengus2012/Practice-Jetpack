package nlab.practice.jetpack.util.recyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 의 세부설정을 처리하는 객체 정의
 *
 * @author Doohyun
 */
data class RecyclerViewConfig(
        var layoutManager: RecyclerView.LayoutManager? = null,
        val itemDecorations: MutableList<RecyclerView.ItemDecoration> = ArrayList(),
        val itemTouchHelpers: MutableList<ItemTouchHelper.Callback> = ArrayList()
)