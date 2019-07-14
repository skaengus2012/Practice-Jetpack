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

package nlab.practice.jetpack.util.recyclerview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.recyclerview.scroller.CenterLinearSmoothScrollerFactory
import nlab.practice.jetpack.util.recyclerview.scroller.CenterSmoothScrollLayoutManager

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class LayoutManagerFactory(private val context: Context) {

    fun createGridLayoutManager(spanCount: Int) = GridLayoutManager(context, spanCount)

    fun createLinearLayoutManager(
        @RecyclerView.Orientation
        orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ): LinearLayoutManager {
        return LinearLayoutManager(context, orientation, reverseLayout)
    }

    fun createCenterScrollerLayoutManager(
        scrollerSpeed: Float = CenterLinearSmoothScrollerFactory.DEFAULT_SCROLL_SPEEND,
        factor: Float = CenterLinearSmoothScrollerFactory.DEFAULT_FACTOR,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ): CenterSmoothScrollLayoutManager {
        return CenterSmoothScrollLayoutManager(context, scrollerSpeed, factor, orientation, reverseLayout)
    }

}
