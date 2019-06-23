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

package nlab.practice.jetpack.util

import androidx.annotation.IntDef
import com.google.android.material.appbar.AppBarLayout

/**
 * @author Doohyun
 */
open class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    @IntDef(State.IDLE, State.EXPANDED, State.COLLAPSED)
    annotation class State {
        companion object {
            const val EXPANDED = 0
            const val COLLAPSED = 1
            const val IDLE = 2
        }
    }

    var state = State.IDLE

    override fun onOffsetChanged(appBar: AppBarLayout?, offset: Int) {
        when {
            offset == 0 -> {
                if (state != State.EXPANDED) {
                    onStateChanged(appBar, State.EXPANDED)
                }

                state = State.EXPANDED
            }

            Math.abs(offset) >= appBar?.totalScrollRange ?: 0 -> {
                if (state != State.COLLAPSED) {
                    onStateChanged(appBar, State.COLLAPSED)
                }

                state = State.COLLAPSED
            }

            else -> {
                if (state != State.IDLE) {
                    onStateChanged(appBar, State.IDLE)
                }

                state = State.IDLE
            }
        }
    }

    open fun onStateChanged(appBarLayout: AppBarLayout?, @State state: Int) {}
}