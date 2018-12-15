package nlab.practice.jetpack.util.databinding.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
data class RecyclerViewConfig(
        private var _layoutManager: RecyclerView.LayoutManager? = null,
        private val _itemDecorations: MutableList<RecyclerView.ItemDecoration> = ArrayList()
) {

    fun bindRecyclerView(recyclerView: RecyclerView) {
        // set layoutManager
        (_layoutManager?: LinearLayoutManager(recyclerView.context)).run { recyclerView.layoutManager = this }

        // set itemDecoration
        (0 until _itemDecorations.size).forEach { recyclerView.addItemDecoration(_itemDecorations[it], it) }
    }

    fun addItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
        _itemDecorations.add(itemDecoration)
    }
}