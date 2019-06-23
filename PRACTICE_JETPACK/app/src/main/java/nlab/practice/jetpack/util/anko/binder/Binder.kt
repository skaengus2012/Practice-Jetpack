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

package nlab.practice.jetpack.util.anko.binder

/**
 * Binder 의 기본 인터페이스
 *
 * Callback 을 추가하거나, 삭제, 알림 기능등을 수행
 *
 * @author Doohyun
 */
interface Binder<T> {

    fun addCallback(any: T)

    fun removeCallback()

    fun notifyChanged()

}