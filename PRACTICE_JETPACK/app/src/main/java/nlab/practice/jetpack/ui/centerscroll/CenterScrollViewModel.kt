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

package nlab.practice.jetpack.ui.centerscroll

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.LyricsRepository
import nlab.practice.jetpack.ui.common.viewmodel.SimpleTextItemViewModel
import nlab.practice.jetpack.util.recyclerview.CenteringRecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

/**
 * @author Doohyun
 */
class CenterScrollViewModel @Inject constructor(
        lyricsRepository: LyricsRepository,
        layoutManagerFactory: LayoutManagerFactory,
        private val recyclerViewUsecase: CenteringRecyclerViewUsecase) {

    private var currentScrollIndex = 0

    val items = ObservableArrayList<SimpleTextItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createLinearLayoutManager()
    }

    init {
        lyricsRepository.getLyrics()
                .asSequence()
                .map { SimpleTextItemViewModel(it) }
                .run { items.addAll(this) }

    }

    fun scrollToPrevIndex() {
        currentScrollIndex = max(0, currentScrollIndex - 1)

        scrollToIndexInternal(currentScrollIndex)
    }

    fun scrollToNextIndex() {
        currentScrollIndex = min(items.size - 1, currentScrollIndex + 1)

        scrollToIndexInternal(currentScrollIndex)
    }

    private fun scrollToIndexInternal(currentIndex: Int) {
        for ((index, value) in items.withIndex()) {
            value.selected = (index == currentIndex)
        }

        recyclerViewUsecase.center(currentIndex)
    }
}