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

package nlab.practice.jetpack.ui.slide

import android.view.View

/**
 * @author Doohyun
 */
class SlidingHolderViewUsecase(
    private val containerViewSupplier: () -> View,
    private val miniPlayerViewSupplier: () -> View?
) {

    private val containerView: View
        get() = containerViewSupplier()

    private val miniPlayerView: View?
        get() = miniPlayerViewSupplier()

    fun onCollapseState() {
        setContainerAlphaValue(0.0f)
        setMiniPlayerAlphaValue(1.0f)
    }

    fun onExpandState() {
        setContainerAlphaValue(1.0f)
        setMiniPlayerAlphaValue(0.0f)
    }

    fun setContainerAlphaValue(offset: Float) {
        containerView.alpha = offset
    }

    fun setMiniPlayerAlphaValue(offset: Float) {
        miniPlayerView?.alpha = offset
    }

    fun bringToFrontContainer() {
        /**
         * NOTE : bringToFront z 축을 맨 위로 올리는 것을 의미
         */
        containerView.bringToFront()
    }

    fun bringToFrontMiniPlayer() {
        miniPlayerView?.bringToFront()
    }
}