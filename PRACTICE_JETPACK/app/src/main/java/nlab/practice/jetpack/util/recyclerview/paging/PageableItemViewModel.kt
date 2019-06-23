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

import nlab.practice.jetpack.util.recyclerview.Different
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Different 을 지원하는 BindingItemViewModel
 *
 * @author Doohyun
 */
abstract class PageableItemViewModel<T: Different<T>> constructor(val item: T):
        BindingItemViewModel(),
        Different<PageableItemViewModel<T>> {

    override fun areItemsTheSame(newItem: PageableItemViewModel<T>): Boolean = item.areItemsTheSame(newItem.item)

    override fun areContentsTheSame(newItem: PageableItemViewModel<T>): Boolean = item.areContentsTheSame(newItem.item)

    override fun getChangePayload(newItem: PageableItemViewModel<T>): Any? = item.getChangePayload(newItem.item)
}

