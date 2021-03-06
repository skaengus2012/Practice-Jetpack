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

package nlab.practice.jetpack.util.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Header, Footer, Content 를 지원하는 어댑터
 *
 * @author Doohyun
 */
abstract class AbsGenericItemAdapter<T, VIEW_HOLDER : AbsGenericItemAdapter.GenericItemViewHolder<T>> :
    RecyclerView.Adapter<VIEW_HOLDER>() {

    var items: MutableList<T>? = null
    var headers: MutableList<T>? = null
    var footers: MutableList<T>? = null

    override fun getItemCount(): Int {
        var itemSize = items?.size ?: 0

        // header & footer 의 사이즈를 고려해서 처리
        itemSize += headers?.size ?: 0
        itemSize += footers?.size ?: 0

        return itemSize
    }

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        getItemWithCategory(position)?.run { holder.onBind(this) }
    }

    protected fun getItemWithCategory(position: Int): T? {
        val headerSize = headers?.size ?: 0
        val itemSize = items?.size ?: 0
        val footerSize = footers?.size ?: 0

        val headerContentSize = headerSize + itemSize
        val totalSize = headerContentSize + footerSize

        return when {
            // Content 에 데이터가 존재할 경우
            itemSize != 0 && position in headerSize until headerContentSize -> {
                val contentPosition = position - headerSize

                items?.get(contentPosition)
            }

            // Header 에 데이터가 존재할 경우
            headerSize != 0 && position in 0 until headerSize -> headers?.get(position)

            // footer 에 데이터가 존재할 경우
            footerSize != 0 && position in headerContentSize until totalSize -> {
                val footerPosition = position - headerContentSize

                footers?.get(footerPosition)
            }

            else -> null
        }
    }

    abstract class GenericItemViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: T)
    }
}
