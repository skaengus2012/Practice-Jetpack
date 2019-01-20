package nlab.practice.jetpack.util.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter

private typealias ViewModelList = List<BindingItemViewModel>

/**
 * RecyclerView DataBindingAdapter 정의
 *
 * @author Doohyun
 */

@BindingAdapter(value = ["list_items", "list_headers", "list_footers", "list_config"], requireAll = false)
fun bindRecyclerView(
        recyclerView: RecyclerView,
        items: ViewModelList? = null,
        headers: ViewModelList? = null,
        footers: ViewModelList?  = null,
        config: RecyclerViewConfig? = null) {
    // 어댑터 세팅
    if (recyclerView.adapter == null) {
        recyclerView.adapter = BindingItemAdapter()
    }

    bindConfig(recyclerView, config)

    headers?.run { bindHeaders(recyclerView, this) }
    items?.run { bindItems(recyclerView, this) }
    footers?.run { bindFooters(recyclerView, this) }

    recyclerView.adapter?.notifyDataSetChanged()
}

@BindingAdapter(value = ["list_pageadapter", "list_config"], requireAll = false)
fun bindRecyclerViewAdapter(
        recyclerView: RecyclerView,
        adapter: BindingPagedListAdapter<out BindingItemViewModel>,
        config: RecyclerViewConfig? = null) {
    recyclerView.adapter = adapter
    bindConfig(recyclerView, config)
}

/**
 * RecyclerView Config 세팅
 *
 * 추가해야하는 세팅이 존재할 때, 해당 메소드에 기능을 추가할 것
 */
private fun bindConfig(recyclerView: RecyclerView, config: RecyclerViewConfig?) {
    config?.run {
        // 레이아웃 매니저 설정
        recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(recyclerView.context)

        // 아이템 데코레이션 정의
        for (index in 0 until itemDecorations.size) {
            recyclerView.addItemDecoration(itemDecorations[index], index)
        }
    }

    // 레이아웃 매니저 정의
    recyclerView.layoutManager = config?.layoutManager ?: LinearLayoutManager(recyclerView.context)

}

private fun bindHeaders(recyclerView: RecyclerView, items: ViewModelList) {
    getAdapter(recyclerView)?.headers = items.toMutableList()
}


private fun bindItems(recyclerView: RecyclerView, items: ViewModelList) {
    getAdapter(recyclerView)?.items = items.toMutableList()
}

private fun bindFooters(recyclerView: RecyclerView, items: ViewModelList) {
    getAdapter(recyclerView)?.footers = items.toMutableList()
}

private fun getAdapter(recyclerView: RecyclerView): BindingItemAdapter? = recyclerView.adapter?.let {
    it as? BindingItemAdapter
}

