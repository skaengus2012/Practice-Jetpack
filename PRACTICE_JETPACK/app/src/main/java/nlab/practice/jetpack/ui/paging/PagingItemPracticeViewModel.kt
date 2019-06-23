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

package nlab.practice.jetpack.ui.paging

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModel
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModelImpl
import nlab.practice.jetpack.ui.common.viewmodel.PagingTheme
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
@AutoFactory
class PagingItemPracticeViewModel(
        @Provided private val fragmentNavUsecase: FragmentNavUsecase,
        private val pagingItem: PagingItem,
        private val onClickListener: (FragmentNavUsecase) -> Unit) :
        BindingItemViewModel(),
        PagingItemViewModel by PagingItemViewModelImpl(PagingTheme.BLACK, pagingItem, {onClickListener(fragmentNavUsecase)})