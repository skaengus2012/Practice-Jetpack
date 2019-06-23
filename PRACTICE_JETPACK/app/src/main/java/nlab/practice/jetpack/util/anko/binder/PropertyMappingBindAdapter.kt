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

import androidx.databinding.BaseObservable

/**
 * [propertyBinder] 를 [mapper] 조건에 따라 Binder 의 Generic 을 변경하는 호환자 정의
 *
 * @author Doohyun
 */
class PropertyMappingBindAdapter<T, OBS, PROP: BaseObservable>(
        private val mapper: (OBS) -> PROP,
        private val propertyBinder: PropertyBinder<T, PROP>): Binder<OBS> {

    override fun addCallback(any: OBS) {
        propertyBinder.addCallback(mapper(any))
    }

    override fun removeCallback() {
        propertyBinder.removeCallback()
    }

    override fun notifyChanged() {
        propertyBinder.notifyChanged()
    }
}