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

import kotlin.LazyThreadSafetyMode

/**
 * Public 모드의 Lazy 초기화 정의 (lazy 과정에서 동기화 처리를 수행하지 않음)
 *
 * [block] 을 통하여, 초기화하여 결과 처리
 *
 * @return 초기화 후 추가된 객체
 */
fun <T> lazyPublic(block: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.PUBLICATION, block)