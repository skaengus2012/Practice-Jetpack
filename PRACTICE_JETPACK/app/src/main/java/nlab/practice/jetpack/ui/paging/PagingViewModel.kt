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

package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter

/**
 * fragment_paging 을 공통으로 사용하기 위한 인터페이스 정의
 *
 * @author Doohyun
 * @since 2019. 01. 29
 */
interface PagingViewModel : ListErrorPageViewModel {

    fun getListAdapter(): BindingPagedListAdapter<PagingItemPracticeViewModel>

    fun getSubTitle(): ObservableField<String>

    fun getBannerText(): String

    fun isShowRefreshProgressBar(): ObservableBoolean

    fun addItems()

    fun onClickBackButton()
}