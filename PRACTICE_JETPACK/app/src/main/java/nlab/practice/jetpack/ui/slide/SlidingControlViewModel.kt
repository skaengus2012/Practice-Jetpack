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

import androidx.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.PlayController
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingControlViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val playController: PlayController,
        private val disposables: CompositeDisposable) {

    val currentTrack = ObservableField<Track>()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }
    }

    private fun loadCurrentTrack() {
        playController.trackChangeSubject
                .doOnNext { currentTrack.set(it) }
                .subscribe()
                .addTo(disposables)
    }

    fun onClickPlayed() {
        playController.play()
    }

    fun onClickPrev() {
        playController.prev()
    }

    fun onClickNext() {
        playController.next()
    }

    fun onPanelLayoutClick() {
        slidingUpPanelLayoutUsecase?.expand()
    }
}