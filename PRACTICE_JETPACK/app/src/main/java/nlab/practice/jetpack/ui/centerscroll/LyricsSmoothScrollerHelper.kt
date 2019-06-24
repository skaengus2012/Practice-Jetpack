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

import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

private const val SKIP_RANGE = 5

/**
 * @author Doohyun
 * @since 2019. 06. 24
 */
class LyricsSmoothScrollerHelper private constructor(
        private val viewModel: CenterScrollViewModel,
        private val recyclerViewUsecase: RecyclerViewUsecase,
        private val schedulerFactory: SchedulerFactory) {

    fun scrolling(position: Int) {
        val firstRange = recyclerViewUsecase.findFirstVisiblePosition() - SKIP_RANGE
        val lastRange = recyclerViewUsecase.findLastVisiblePosition() + SKIP_RANGE

        when {
            firstRange > position -> min(viewModel.items.size - 1, position + SKIP_RANGE)

            lastRange < position -> max(0, position - SKIP_RANGE)

            else -> null
        }?.let {
            directPosition
            ->
            recyclerViewUsecase.scrollToPosition(directPosition)
        }

        schedulerFactory.ui().scheduleDirect { postScroll() }
    }

    private fun postScroll() {
        viewModel.getCurrentSelectedIndex()?.let { recyclerViewUsecase.smoothScrollToPosition(it) }
    }

    class Factory @Inject constructor(
            private val recyclerViewUsecase: RecyclerViewUsecase,
            private val schedulerFactory: SchedulerFactory) {

        fun create(viewModel: CenterScrollViewModel): LyricsSmoothScrollerHelper {
            return LyricsSmoothScrollerHelper(viewModel, recyclerViewUsecase, schedulerFactory)
        }
    }

}