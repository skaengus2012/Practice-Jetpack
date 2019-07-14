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

package nlab.practice.jetpack.util.recyclerview.scroller

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.recyclerview.isVisiblePosition

/**
 * @author Doohyun
 */
class CenterSmoothScrollLayoutManager(
    context: Context,
    scrollerSpeed: Float,
    factor: Float,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private val visibleScrollerFactory = CenterLinearSmoothScrollerFactory(context, scrollerSpeed, factor)
    private val inVisibleScrollerFactory = CenterLinearSmoothScrollerFactory(context, factor = factor)

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        recyclerView?.let {
            val isVisibleItem = it.isVisiblePosition(position)

            if (isVisibleItem) {
                visibleScrollerFactory.create()
            } else {
                inVisibleScrollerFactory.create()
            }.run {
                targetPosition = position
                startSmoothScroll(this)
            }
        }
    }

}