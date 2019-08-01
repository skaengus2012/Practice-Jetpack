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

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.HistoryRepository
import nlab.practice.jetpack.ui.common.viewmodel.SimpleItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class HistoryViewModel @Inject constructor(
    historyRepository: HistoryRepository,
    private val historyItemFactory: HistoryItemViewModel.Factory
) {
    val headers = SimpleItemViewModel(R.layout.view_history_header).run { listOf(this) }
    val items = historyRepository.items.map { historyItemFactory.create(it) }
}