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

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayController @Inject constructor(private val toastHelper: ToastHelper) {

    private val trackChangeSubject: BehaviorSubject<Track> = BehaviorSubject.create()

    val trackChangedObservable: Observable<Track>
        get() = trackChangeSubject

    val latestTrack: Track?
        get() = trackChangeSubject.value

    fun post(track: Track) {
        trackChangeSubject.onNext(track)
    }

    fun play() {
        toastHelper.showToast(R.string.slide_up_panel_play_message)
    }

    fun prev() {
        toastHelper.showToast(R.string.slide_up_panel_prev_message)
    }

    fun next() {
        toastHelper.showToast(R.string.slide_up_panel_next_message)
    }
}
