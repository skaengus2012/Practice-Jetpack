package nlab.practice.jetpack.util.recyclerview.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private typealias ViewModelList = List<DataBindingItemViewModel>
private typealias ViewModelMutableList = MutableList<DataBindingItemViewModel>

/**
 * RecyclerView DataBindingAdapter 정의
 *
 * @author Doohyun
 */

@BindingAdapter(value = ["list_items", "list_headers", "list_footers", "list_Config"], requireAll = false)
fun bindRecyclerView(
        recyclerView: RecyclerView,
        items: ViewModelList? = null,
        headers: ViewModelList? = null,
        footers: ViewModelList?  = null,
        config: RecyclerViewConfig? = null) {
    bindConfig(recyclerView, config)

    headers?.run { bindHeaders(recyclerView, this) }
    items?.run { bindItems(recyclerView, this) }
    footers?.run { bindFooters(recyclerView, this) }

    recyclerView.adapter?.notifyDataSetChanged()
}

/**
 * RecyclerView Config 세팅
 *
 * 추가해야하는 세팅이 존재할 때, 해당 메소드에 기능을 추가할 것
 */
private fun bindConfig(recyclerView: RecyclerView, config: RecyclerViewConfig?) {
    recyclerView.layoutManager = config?.layoutManager ?: LinearLayoutManager(recyclerView.context)

    if (recyclerView.adapter == null) {
        recyclerView.adapter = DataBindingItemAdapter()
    }
}

private fun bindHeaders(recyclerView: RecyclerView, items: ViewModelList) = getAdapter(recyclerView)?.headers?.run {
    replaceItems(this, items)
}

private fun bindItems(recyclerView: RecyclerView, items: ViewModelList) = getAdapter(recyclerView)?.items?.run {
    replaceItems(this, items)
}

private fun bindFooters(recyclerView: RecyclerView, items: ViewModelList) = getAdapter(recyclerView)?.footers?.run {
    replaceItems(this, items)
}

private fun getAdapter(recyclerView: RecyclerView): DataBindingItemAdapter? = recyclerView.adapter?.let {
    it as? DataBindingItemAdapter
}

private fun replaceItems(target: ViewModelMutableList, newItem: ViewModelList) {
    target.clear()
    target.addAll(newItem)
}


