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

package nlab.practice.jetpack.util.slidingpanel

import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlin.math.max
import kotlin.math.min

/**
 * @author Doohyun
 * @since 2019. 05. 24
 */
class SlidingUpPanelLayoutUsecase(private val viewSupplier: () -> SlidingUpPanelLayout) {

    private val slidingUpPanelLayout: SlidingUpPanelLayout
        get() = viewSupplier()

    val slideOffsetSubject: Subject<Float> = PublishSubject.create()

    val slidePanelStateSubject: Subject<SlidingUpPanelLayout.PanelState> = PublishSubject.create()

    var currentPanelState: SlidingUpPanelLayout.PanelState
        get() = slidingUpPanelLayout.panelState
        set(value) {
            slidingUpPanelLayout.panelState = value
        }

    fun initialize() {
        slidingUpPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                val offset = min(max(0.0f, slideOffset), 1.0f)

                slideOffsetSubject.onNext(offset)
            }

            override fun onPanelStateChanged(
                    panel: View?,
                    previousState: SlidingUpPanelLayout.PanelState?,
                    newState: SlidingUpPanelLayout.PanelState?) {
                newState?.run {  slidePanelStateSubject.onNext(this) }
            }
        })
    }

    fun expand() {
        slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }

    fun collapse() {
        slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    fun hidden() {
        slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
    }
}