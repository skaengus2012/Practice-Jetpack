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

package nlab.practice.jetpack.ui.common.viewmodel

import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem

/**
 * @author Doohyun
 */
interface PagingItemViewModel {
    fun getTitle(): String
    fun getImageUrl(): String?
    fun onClick()
    @LayoutRes fun getLayoutRes(): Int
    @PagingTheme fun getPagingTheme(): Int
}

@IntDef(value = [
    PagingTheme.BLACK, PagingTheme.WHITE
])
annotation class PagingTheme {
    companion object {
        const val WHITE = 0
        const val BLACK = 1
    }
}

class PagingItemViewModelImpl(
        @PagingTheme private val pagingTheme: Int,
        private val pagingItem: PagingItem,
        private val onClickAction: () -> Unit) : PagingItemViewModel {

    override fun getTitle(): String = pagingItem.title

    override fun getImageUrl(): String? = pagingItem.imageUrl

    override fun onClick() {
        onClickAction()
    }

    @PagingTheme override fun getPagingTheme(): Int = pagingTheme

    @LayoutRes override fun getLayoutRes(): Int = R.layout.view_paging_item
}