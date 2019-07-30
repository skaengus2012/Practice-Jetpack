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

import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.Observable

/**
 * @author Doohyun
 * @since 2019. 07. 29
 */
class MainBottomNavigationViewUsecase(private val viewSupplier: () -> BottomNavigationView) {

    private val view: BottomNavigationView
        get() = viewSupplier()

    var selectedItemId: Int
        get() = view.selectedItemId
        set(value) {
            view.selectedItemId = value
        }

    fun onSelected(validItemIdPredicate: (itemId: Int) -> Boolean): Observable<Int> = Observable.create { emitter ->

        view.setOnNavigationItemSelectedListener {
            val testResult = validItemIdPredicate(it.itemId)
            if (testResult) {
                emitter.onNext(it.itemId)
            }

            testResult
        }

    }

    fun onReSelected(): Observable<Int> = Observable.create { emitter ->

        view.setOnNavigationItemReselectedListener {
            emitter.onNext(it.itemId)
        }

    }
}