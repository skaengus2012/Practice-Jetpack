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

/**
 * @author Doohyun
 */
interface Different<T> {
    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
    fun getChangePayload(newItem: T): Any?
}

class DifferentDelegate<ViewModel, Item : Different<Item>>(
    private val myItemSupplier: () -> Item,
    private val itemConvertFunction: (viewModel: ViewModel) -> Item
) : Different<ViewModel> {

    override fun areItemsTheSame(newItem: ViewModel): Boolean {
        return myItemSupplier().areItemsTheSame(itemConvertFunction(newItem))
    }

    override fun areContentsTheSame(newItem: ViewModel): Boolean {
        return myItemSupplier().areContentsTheSame(itemConvertFunction(newItem))
    }

    override fun getChangePayload(newItem: ViewModel): Any? {
        return myItemSupplier().getChangePayload(itemConvertFunction(newItem))
    }
}