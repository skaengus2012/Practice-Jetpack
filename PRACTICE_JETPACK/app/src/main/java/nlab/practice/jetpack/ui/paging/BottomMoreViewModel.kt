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

import androidx.databinding.Bindable
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 01. 30
 */
class BottomMoreViewModel(private val onClick: () -> Unit) : BindingItemViewModel() {

    @Bindable
    var showProgress = true
    set(value) {
        field = value
        notifyPropertyChanged(BR.showProgress)
    }

    override fun getLayoutRes(): Int = R.layout.view_paging_bottom_more

    fun onClickErrorButton() {
        showProgress = true
        onClick()
    }
}