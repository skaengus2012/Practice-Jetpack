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

import nlab.practice.jetpack.util.di.activity.ActivityCallback
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*

/**
 * Test for MainHolderViewModel
 *
 * 가장 중요하게 봐야할 점 -> 생명주기에 따라 정확한 메소드가 불렸는지가 중요.
 *
 * 유즈케이스
 * 0. 초기상태 테스트
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class MainHolderViewModelTest {

    private val navUseCase = TestMainBottomNavUseCase()

    private val lifecycleBinder = ActivityLifecycleBinder()

    private val activityCallback = ActivityCallback()

    private fun createViewModel() = MainHolderViewModel(
        lifecycleBinder,
        activityCallback,
        navUseCase
    )

    @Test
    fun checkStateOnCreateBehavior() {
        createViewModel()

        // 처음 onCreate 된 상태.
        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)
        assertEquals(MainBottomNavMenuType.MENU_HOME, navUseCase.selectedItemId)

        // 외부에서 타입 변경
        navUseCase.selectedItemId = MainBottomNavMenuType.MENU_HISTORY

        // 화면 전환/액티비티 활성 제거 후 되살아날 때 상태
        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)
        assertEquals(MainBottomNavMenuType.MENU_HISTORY, navUseCase.selectedItemId)

        // 외부에서 타입 변경
        navUseCase.selectedItemId = MainBottomNavMenuType.MENU_HOME

        // 화면 전환/액티비티 활성 제거 후 되살아날 때 상태
        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)
        assertEquals(MainBottomNavMenuType.MENU_HOME, navUseCase.selectedItemId)
    }

}