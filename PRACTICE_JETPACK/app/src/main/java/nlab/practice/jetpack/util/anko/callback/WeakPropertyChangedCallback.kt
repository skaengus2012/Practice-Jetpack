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

package nlab.practice.jetpack.util.anko.callback

import androidx.databinding.Observable
import java.lang.ref.WeakReference

/**
 * [targetRef] 에 대한 의존성을 가진 OnPropertyChangedCallback
 *
 * TARGET 의 memory leak 방지를 위한 PropertyChangedCallback
 *
 * @author Doohyun
 * @since 2018. 11. 16
 */
abstract class WeakPropertyChangedCallback<TARGET>(val targetRef: WeakReference<TARGET>)
    : Observable.OnPropertyChangedCallback()