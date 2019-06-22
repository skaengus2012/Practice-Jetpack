package nlab.practice.jetpack.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 * @since 2019. 01. 24
 */
open class RecyclerViewUsecase(viewSupplier: () -> RecyclerView) {

    private val recyclerView: RecyclerView by lazyPublic(viewSupplier)

    fun scrollToPositionWithOffset(position: Int, offset: Int) {
        recyclerView.layoutManager?.let { it as? LinearLayoutManager }?.scrollToPositionWithOffset(position, offset)
    }

    fun smoothScrollToPosition(position: Int) {
        recyclerView.smoothScrollToPosition(position)
    }
}