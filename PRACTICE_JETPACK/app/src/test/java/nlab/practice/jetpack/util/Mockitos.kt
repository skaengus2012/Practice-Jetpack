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

import org.mockito.Mockito

/**
 * Mockito 의 일반 any 가 nullable 상태로 방출.
 * nonnull 데이터를 내기 위해 다음과 같이 처리
 */
@Suppress("UNCHECKED_CAST")
fun <T> anyNonNull(): T {
    Mockito.any<T>()
    return null as T
}