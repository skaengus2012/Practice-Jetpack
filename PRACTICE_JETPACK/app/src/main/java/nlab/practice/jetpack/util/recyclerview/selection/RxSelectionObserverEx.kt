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

package nlab.practice.jetpack.util.recyclerview.selection

import androidx.annotation.IntDef
import androidx.recyclerview.selection.SelectionTracker
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 */
class RxSelectionObserverEx<T> : SelectionTracker.SelectionObserver<T>() {

    val subject: PublishSubject<SelectionEvent<T>> = PublishSubject.create()

    override fun onSelectionRefresh() {
        subject.onNext(SelectionEvent(SelectionEvent.Code.REFRESH))
    }

    override fun onSelectionRestored() {
        subject.onNext(SelectionEvent(SelectionEvent.Code.RESTORED))
    }

    override fun onItemStateChanged(key: T, selected: Boolean) {
        subject.onNext(SelectionEvent(SelectionEvent.Code.STATE_CHANGED, key, selected))
    }

    override fun onSelectionChanged() {
        subject.onNext(SelectionEvent(SelectionEvent.Code.SELECTION_CHANGED))
    }
}

class SelectionEvent<T>(@Code val eventCode: Int, val key: T? = null, val selected: Boolean? = null) {

    @IntDef(value = [
        Code.REFRESH,
        Code.RESTORED,
        Code.STATE_CHANGED,
        Code.SELECTION_CHANGED
    ])
    annotation class Code {
        companion object {
            const val REFRESH = 0
            const val RESTORED = 1
            const val STATE_CHANGED = 2
            const val SELECTION_CHANGED = 3
        }
    }
}