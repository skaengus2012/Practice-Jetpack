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

package nlab.practice.jetpack

import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Test

/**
 * @author Doohyun
 */
class SimpleTest {

    @Test
    fun testSubList() {
        val ints: List<Int> = (0..100).map { it }

        Observable.fromIterable(ints).skip(91).take(10).toList().doOnSuccess { println(it) }.subscribe()
    }

    @Test
    fun testMaybeFilter() {
        Maybe.fromCallable { 1 }.doOnComplete { println("비어있음") }.subscribe()
    }
}