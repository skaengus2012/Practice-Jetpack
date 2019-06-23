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

package nlab.practice.jetpack.ui.common.viewmodel

import androidx.databinding.ObservableBoolean

/**
 * view_list_error_page.xml 에 대응되는 뷰모델 정의
 *
 * @author Doohyun
 * @since 2019. 02. 08
 */
interface ListErrorPageViewModel {
    fun isShowErrorView(): ObservableBoolean
    fun refresh()
}