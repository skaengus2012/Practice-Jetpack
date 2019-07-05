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
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 */
class CenterLinearSmoothScrollerFactory(
        private val context: Context,
        private val scrollSpeed: Float = DEFAULT_SCROLL_SPEEND,
        private val factor: Float = DEFAULT_FACTOR
) : SmoothScrollerFactory {

    override fun create(): RecyclerView.SmoothScroller = SmoothScrollerEx()

    inner class SmoothScrollerEx : LinearSmoothScroller(context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return displayMetrics
                    ?.run { scrollSpeed / displayMetrics.densityDpi }
                    ?: super.calculateSpeedPerPixel(displayMetrics)
        }

        override fun calculateDtToFit(
                viewStart: Int,
                viewEnd: Int,
                boxStart: Int,
                boxEnd: Int,
                snapPreference: Int
        ): Int {
            return ((boxStart + (boxEnd - boxStart) / factor) - (viewStart + (viewEnd - viewStart) / factor)).toInt()
        }
    }

    companion object {
        const val DEFAULT_SCROLL_SPEEND = 75f
        const val DEFAULT_FACTOR = 2f
    }
}