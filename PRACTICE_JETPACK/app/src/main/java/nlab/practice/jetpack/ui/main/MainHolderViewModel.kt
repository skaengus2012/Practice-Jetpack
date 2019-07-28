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

package nlab.practice.jetpack.ui.main

import androidx.databinding.BaseObservable
import nlab.practice.jetpack.util.di.activity.ActivityCallback
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * MainHolderActivity 의 ViewModel
 *
 * @author Doohyun
 */
class MainHolderViewModel @Inject constructor(
    activityLifeCycleBinder: ActivityLifeCycleBinder,
    activityCallback: ActivityCallback,
    private val mainNavUsecase: MainBottomNavUsecase): BaseObservable() {

    init {
        activityLifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) { executeOnCreate() }
        activityCallback.onBackPressed(this::executeOnBackPressed)
        activityCallback.onRestoreInstanceState { executeOnRestoreInstanceState() }
    }

    private fun executeOnCreate() {
        mainNavUsecase.initialize()
    }

    private fun executeOnBackPressed(): Boolean = when {
        // 현재 Primary 화면의 OnBackPressed 의 처리 결과에 따라 이 후 작업 결정
        mainNavUsecase.onBackPressedInPrimaryNav() -> true

        // 현재 메뉴가 Home 이 아닐 경우 홈으로 이동
        !mainNavUsecase.isHome() -> {
            mainNavUsecase.navHome()
            true
        }

        // 그 외 거짓
        else -> false
    }

    private fun executeOnRestoreInstanceState() {
        mainNavUsecase.refreshPage()
    }

}