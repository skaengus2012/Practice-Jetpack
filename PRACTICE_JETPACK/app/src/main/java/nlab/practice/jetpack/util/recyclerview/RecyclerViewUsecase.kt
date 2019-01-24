package nlab.practice.jetpack.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 * @since 2019. 01. 24
 */
class RecyclerViewUsecase(private val _recyclerView: () -> RecyclerView) {

    private fun getRecyclerView(): RecyclerView = _recyclerView()

    fun scrollToPositionWithOffset(position: Int, offset: Int) {
        getRecyclerView().layoutManager?.let { it as? LinearLayoutManager }?.scrollToPositionWithOffset(position, offset)
    }
}