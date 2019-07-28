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
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 */
class RxBaseObservables private constructor(baseObservable: androidx.databinding.Observable) {

    companion object {
        fun of(baseObservable: androidx.databinding.Observable) = RxBaseObservables(baseObservable)
    }

    private val subject = PublishSubject.create<Event>()

    init {
        baseObservable.addOnPropertyChangedCallback(object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: androidx.databinding.Observable?, propertyId: Int) {
                subject.onNext(Event(sender, propertyId))
            }
        })
    }

    fun toObservable(): Observable<Event> = subject

    data class Event(
        private val sender: androidx.databinding.Observable?,
        private val propertyId: Int
    )

}