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

package nlab.practice.jetpack.ui.listadapter

import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.Different
import nlab.practice.jetpack.util.recyclerview.DifferentDelegate
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import nlab.practice.jetpack.util.recyclerview.selection.Selectable

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
@AutoFactory
class ListAdapterExampleItemViewModel(
        private val pagingItem: PagingItem) :
        BindingItemViewModel(),
        Different<ListAdapterExampleItemViewModel> by DifferentDelegate({ pagingItem }, { viewModel -> viewModel.pagingItem }),
        Selectable<Long> {

    @Bindable
    var selectState = false
    set(value) {
        field = value
        notifyPropertyChanged(BR.selectState)
    }

    override fun getLayoutRes(): Int = R.layout.view_list_adapter_grid_item

    override fun getSelectionKey(): Long = pagingItem.itemId.toLong()

    @Bindable
    fun getImageUrl(): String? = pagingItem.imageUrl

    @Bindable
    fun getTitle(): String? = pagingItem.title
}