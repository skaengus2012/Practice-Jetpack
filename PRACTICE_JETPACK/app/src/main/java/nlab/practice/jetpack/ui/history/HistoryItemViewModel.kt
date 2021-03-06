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

package nlab.practice.jetpack.ui.history

import dagger.Reusable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.History
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class HistoryItemViewModel(
    resourceProvider: ResourceProvider,
    private val history: History
) : BindingItemViewModel() {

    private val _resultMessage = when (history.isSuccess) {
        true -> R.string.history_success
        false -> R.string.history_failed
    }.run { resourceProvider.getString(this) }

    val title: String
        get() = history.title

    val subTitle: String
        get() = history.subTitle

    val resultMessage: String
        get() = _resultMessage

    override fun getLayoutRes(): Int = R.layout.view_history_item

    @Reusable
    class Factory @Inject constructor(private val resourceProvider: ResourceProvider) {
        fun create(history: History) = HistoryItemViewModel(resourceProvider, history)
    }
}