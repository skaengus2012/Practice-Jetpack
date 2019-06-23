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

import android.view.View

/**
 * Binder 클래스의 확장체
 *
 * 데이터바인딩의 BindingAdapter 역할을 할 수 있음
 *
 * @author Doohyun
 */


/**
 * onClick 에 대한 리스너 연결을 수행한다.
 */
inline fun <T: View, OBS> PropertyBinder<T, OBS>.onClick(crossinline onClickBehavior: View.(OBS)-> Unit) : T = drive {
    observable
    ->
    setOnClickListener {
        onClickBehavior(it, observable)
    }
}