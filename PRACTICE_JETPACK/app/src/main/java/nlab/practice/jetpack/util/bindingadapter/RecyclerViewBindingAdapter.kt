/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.util.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

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
    footers: ViewModelList? = null,
    config: RecyclerViewConfig? = null
) {
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

@BindingAdapter(value = ["list_adapter", "list_config"], requireAll = false)
fun bindRecyclerViewAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    config: RecyclerViewConfig? = null
) {
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
        (0 until itemDecorations.size)
            .forEach { recyclerView.addItemDecoration(itemDecorations[it], it) }

        // 아이템 터치 헬퍼 정의
        (0 until itemTouchHelperSuppliers.size)
            .forEach { itemTouchHelperSuppliers[it].attachToRecyclerView(recyclerView) }
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

