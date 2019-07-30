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
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 */
class SlidingMainViewModel @Inject constructor(
    private val playController: PlayController,
    private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
    lifeCycleBinder: FragmentLifecycleBinder
) {

    private val disposables = CompositeDisposable()

    val currentTrack = ObservableField<Track>()

    val artist = ObservableField<String>()

    init {
        lifeCycleBinder.bindUntil(FragmentLifecycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }

        lifeCycleBinder.bindUntil(FragmentLifecycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }
    }

    private fun loadCurrentTrack() {
        playController.trackChangedObservable
            .doOnNext {
                currentTrack.set(it)
                artist.set("Suzy")
            }
            .subscribe()
            .addTo(disposables)
    }

    fun onPanelCollapse() {
        slidingUpPanelLayoutUsecase?.collapsed()
    }

    fun onPlay() {
        playController.play()
    }

    fun onPrev() {
        playController.prev()
    }

    fun onNext() {
        playController.next()
    }
}