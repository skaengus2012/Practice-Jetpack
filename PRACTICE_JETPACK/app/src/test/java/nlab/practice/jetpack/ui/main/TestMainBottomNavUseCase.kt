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

package nlab.practice.jetpack.ui.main

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 */
class TestMainBottomNavUseCase : MainBottomNavUseCase {

    private var _selectedItemId: Int = 0

    private val selectSubject = PublishSubject.create<Int>()
    private val reSelectSubject = PublishSubject.create<Int>()

    override var selectedItemId: Int
        get() = _selectedItemId
        set(value) {
            when(value) {
                _selectedItemId -> reSelectSubject.onNext(value)

                else -> {
                    _selectedItemId = value
                    selectSubject.onNext(_selectedItemId)
                }
            }
        }

    override fun onSelected(validItemIdPredicate: (itemId: Int) -> Boolean): Observable<Int> {
        return selectSubject.filter { validItemIdPredicate(it) }
    }

    override fun onReSelected(): Observable<Int> = reSelectSubject
}