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

package nlab.practice.jetpack.util.lifecycle

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

/**
 * LifeCycle 중계기
 *
 * @author Doohyun
 */
class LifeCycleBinder<T> {

    val disposables = CompositeDisposable()

    val subject: PublishSubject<T> = PublishSubject.create()

    inline fun bindUntil(code: T, crossinline action: () -> Unit) {
        subject.filter { it == code }
            .doOnNext { action() }
            .subscribe()
            .addTo(disposables)
    }

    fun apply(event: T) = subject.onNext(event)

    fun clear() {
        disposables.clear()
    }
}