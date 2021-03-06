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

package nlab.practice.jetpack.util.recyclerview.binding

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import nlab.practice.jetpack.util.di.itemview.ItemViewUsecaseFactory

/**
 * @author Doohyun
 */
abstract class BindingItemViewModel: BaseObservable() {
    // 필요하다면, 이쪽의 데이터 변경 시점을 통해 처리해볼 수 있음.
    var itemViewUsecaseFactory: ItemViewUsecaseFactory? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int
}