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

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 */
class ListMappingBindAdapter<T: RecyclerView, U, ITEM>(
        private val mapper: (U)-> List<ITEM>,
        private val listBinder: ListBinder<T, ITEM>) : Binder<U> {

    override fun addCallback(any: U) {
       listBinder.addCallback(mapper(any))
    }

    override fun removeCallback() {
        listBinder.removeCallback()
    }

    override fun notifyChanged() {
        listBinder.notifyChanged()
    }
}