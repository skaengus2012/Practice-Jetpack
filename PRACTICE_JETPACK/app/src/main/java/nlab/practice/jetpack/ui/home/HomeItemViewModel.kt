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

package nlab.practice.jetpack.ui.home

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Home 에서 사용하는 Item 정의
 *
 * @author Doohyun
 */
class HomeItemViewModel(
    private val testMenu: TestMenu,
    private val navigateAction: () -> Unit
) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_home_item

    val visibleCardTitle: Boolean
        get() = testMenu.cardTitle?.isNotEmpty() ?: false

    val cardTitle: String?
        get() = testMenu.cardTitle

    val cardColor: Int
        get() = testMenu.cardColorRes

    val title: String?
        get() = testMenu.title

    fun onClick() {
        navigateAction()
    }
}