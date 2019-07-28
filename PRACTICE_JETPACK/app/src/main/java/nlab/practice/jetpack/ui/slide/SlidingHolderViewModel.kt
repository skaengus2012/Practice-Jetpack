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

import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.isCollapsed
import nlab.practice.jetpack.util.slidingpanel.isExpanded
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingHolderViewModel @Inject constructor(
    fragmentLifeCycleBinder: FragmentLifeCycleBinder,
    private val schedulerFactory: SchedulerFactory,
    private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
    private val slideHolderViewUsecase: SlidingHolderViewUsecase,
    private val slidingHolderTransitionUsecase: SlidingHolderTransitionUsecase
) {

    private val disposables = CompositeDisposable()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_ACTIVITY_CREATED) { doOnActivityCreated() }
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY_VIEW) { disposables.clear() }
    }

    private fun doOnActivityCreated() {
        subscribeSlideOffset()
        subscribeSlideState()

        schedulerFactory.ui()
            .scheduleDirect {
                initializeSlideOffset()
                slidingHolderTransitionUsecase.replaceMainFragment()
            }
            .addTo(disposables)
    }

    private fun initializeSlideOffset() {
        slidingUpPanelLayoutUsecase?.currentPanelState?.let {
            when (it) {
                SlidingUpPanelLayout.PanelState.EXPANDED -> slideHolderViewUsecase.onExpandState()

                SlidingUpPanelLayout.PanelState.COLLAPSED -> slideHolderViewUsecase.onCollapseState()

                else -> {
                }
            }
        }
    }

    private fun subscribeSlideOffset() {
        slidingUpPanelLayoutUsecase?.slideOffsetSubject
            ?.doOnNext { offset
                ->
                slideHolderViewUsecase.setContainerAlphaValue(min((offset * 2), 1.0f))
                slideHolderViewUsecase.setMiniPlayerAlphaValue(max(1 - (offset * 7), 0.0f))
            }
            ?.subscribe()
            ?.addTo(disposables)
    }

    private fun subscribeSlideState() {
        slidingUpPanelLayoutUsecase?.slidePanelStateSubject
            ?.subscribe {
                when {
                    it.isExpanded() -> slideHolderViewUsecase.bringToFrontContainer()
                    it.isCollapsed() -> slideHolderViewUsecase.bringToFrontMiniPlayer()
                    else -> {
                    }
                }
            }
            ?.addTo(disposables)
    }
}