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

package nlab.practice.jetpack.util.anko.binder

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

private typealias LCallback<ITEM> = ObservableList.OnListChangedCallback<ObservableList<ITEM>>

/**
 * ObservableList 에 대한 Binder 정의
 *
 * 오직 RecyclerView 타겟에 대해 List Type 을 셋할 수 있음
 *
 * @author Doohyun
 */
class ListBinder<T: RecyclerView, ITEM>(private val target: T): Binder<List<ITEM>> {

    private var observableList: List<ITEM>? = null
    private var onListChangedCallback = ArrayList<LCallback<ITEM>>()

    override fun addCallback(any: List<ITEM>) {
        observableList = any
        onListChangedCallback.forEach { any.toObservableList()?.addOnListChangedCallback(it) }
    }

    override fun removeCallback() {
        onListChangedCallback.forEach { observableList?.toObservableList()?.removeOnListChangedCallback(it) }
    }

    override fun notifyChanged() {
        onListChangedCallback.forEach { observableList?.toObservableList()?.run { it.onChanged(this) } }
    }

    fun drive(callback: LCallback<ITEM>) : T {
        onListChangedCallback.add(callback)
        observableList?.toObservableList()?.addOnListChangedCallback(callback)

        return target
    }

    private fun List<ITEM>.toObservableList(): ObservableList<ITEM>? {
        @Suppress("UNCHECKED_CAST")
        return this as? ObservableList<ITEM>
    }
}