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

package nlab.practice.jetpack.util.recyclerview.paging

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import nlab.practice.jetpack.util.recyclerview.DiffCallback
import nlab.practice.jetpack.util.recyclerview.DiffCallbackEx
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewHolder
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Paging 작업을 수행하는 Adapter 정의
 *
 * @author Doohyun
 */
class BindingPagedListAdapter<T : BindingItemViewModel>(
    callback: DiffCallback<T>? = null,
    val bottomMoreItem: BindingItemViewModel? = null,
    @LayoutRes val placeholderResId: Int = 0
) : PagedListAdapter<T, BindingItemViewHolder>(callback ?: DiffCallbackEx()) {

    var isShowBottomProgress = false
        set(value) {
            field = value
            notifyItemChanged(super.getItemCount())
        }

    override fun getItemCount(): Int = super.getItemCount() + if (isNeedMoreBottom()) {
        1
    } else {
        0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingItemViewHolder =
        BindingItemViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: BindingItemViewHolder, position: Int) {
        if (isNeedOnlyCommonCase()) {
            getItem(position)?.run { holder.onBind(this) }
        } else {
            val itemCount = super.getItemCount()
            if (position < itemCount) {
                getItem(position)
            } else {
                bottomMoreItem
            }?.run { holder.onBind(this) }
        }
    }

    override fun getItemViewType(position: Int): Int = if (isNeedOnlyCommonCase()) {
        getItem(position)?.getLayoutRes() ?: placeholderResId
    } else {
        val itemCount = super.getItemCount()
        if (position < itemCount) {
            getItem(position)
        } else {
            bottomMoreItem
        }?.getLayoutRes() ?: 0
    }


    /**
     * 오리지널 function 을 사용해야하는지 여부
     *
     * placeHolder 를 사용하거나, BottomMoreItem 을 사용하지 않으면 기본 기능만 수행한다.
     */
    private fun isNeedOnlyCommonCase(): Boolean {
        val isUsePlaceholder = placeholderResId != 0
        val isNoneUseBottomMoreItem = (bottomMoreItem == null)

        return isUsePlaceholder || isNoneUseBottomMoreItem
    }

    private fun isNeedMoreBottom(): Boolean {
        return placeholderResId == 0 && bottomMoreItem != null && isShowBottomProgress
    }
}
