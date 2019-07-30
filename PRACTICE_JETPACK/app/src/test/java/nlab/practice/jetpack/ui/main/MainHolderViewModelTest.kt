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

import io.reactivex.subjects.PublishSubject
import nlab.practice.jetpack.util.anyNonNull
import nlab.practice.jetpack.util.di.activity.ActivityCallback
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Test for MainHolderViewModel
 *
 * 가장 중요하게 봐야할 점 -> 생명주기에 따라 정확한 메소드가 불렸는지가 중요.
 *
 * 유즈케이스
 * 0. 초기상태 테스트
 * 1. onCreate 시점 현재 BottomNavigationView 가 설정된 페이지에 대한 업데이트 처리가 필요함.
 * 2. onSelected 시점 액션 테스트
 * 3. onReSelected 시점 액션 테스트
 * 4. onBackPressedIfContainerBackPressedTrue 시점 테스트
 * 5. onRestoreInstanceState 고려 필요 -> BottomNavigationView 의 경우 복구 시, 바로 현재 menuId 를 알려주지 않음
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class MainHolderViewModelTest {

    private val lifecycleBinder = ActivityLifecycleBinder()

    private val activityCallback = ActivityCallback()

    private val selectTabSubject = PublishSubject.create<Int>()

    private val reSelectTabSubject = PublishSubject.create<Int>()

    @Mock
    lateinit var navController: MainNavController

    @Mock
    lateinit var bottomNavigationViewUsecase: MainBottomNavigationViewUsecase

    private fun createViewModel() = MainHolderViewModel(
        lifecycleBinder,
        activityCallback,
        bottomNavigationViewUsecase,
        navController
    )

    @Before
    fun initialize() {
        `when`(bottomNavigationViewUsecase.onSelected(anyNonNull())).thenReturn(selectTabSubject)
        `when`(bottomNavigationViewUsecase.onReSelected()).thenReturn(reSelectTabSubject)
    }

    @Test
    fun doOnCreateBehavior() {
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HOME)

        createViewModel()

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navController, times(1)).navHome()
    }

    @Test
    fun selectBottomTab() {
        createViewModel()
        postActivityLifecycle()

        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HISTORY)
        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)
        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HISTORY)

        verify(navController, times(1)).navHome()
        verify(navController, times(2)).navHistory()
    }

    @Test
    fun reselectedBottomTabIfChildHasStack() {
        `when`(navController.invokeContainerReselected()).thenReturn(true)

        createViewModel()
        postActivityLifecycle()

        reSelectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)

        verify(navController, times(1)).invokeContainerReselected()
        verify(navController, never()).clearContainerChildren()
    }

    @Test
    fun reselectedBottomTabIfChildEmptyStack() {
        `when`(navController.invokeContainerReselected()).thenReturn(false)

        createViewModel()
        postActivityLifecycle()

        reSelectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)

        verify(navController, times(1)).invokeContainerReselected()
        verify(navController, times(1)).clearContainerChildren()
    }

    private fun postActivityLifecycle() {
        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)
        lifecycleBinder.apply(ActivityLifecycle.ON_START)
        lifecycleBinder.apply(ActivityLifecycle.ON_RESUME)
    }

    @Test
    fun onBackPressedIfContainerBackPressedTrue() {
        `when`(navController.invokeContainerBackPressed()).thenReturn(true)

        createViewModel()

        val backPressedResult = activityCallback.onBackPressedCommand!!.invoke()
        assertEquals(true, backPressedResult)
        verify(navController, times(1)).invokeContainerBackPressed()
        verify(bottomNavigationViewUsecase, never()).selectedItemId
        verify(navController, never()).navHome()
    }

    @Test
    fun onBackPressedIfContainerBackPressedFalseAndCurrentNotHome() {
        `when`(navController.invokeContainerBackPressed()).thenReturn(false)
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HISTORY)

        createViewModel()

        val backPressedResult = activityCallback.onBackPressedCommand!!.invoke()
        assertEquals(true, backPressedResult)
        verify(bottomNavigationViewUsecase, times(1)).selectedItemId
        verify(navController, times(1)).navHome()
    }

    @Test
    fun onBackPressedIfContainerBackPressedFalseAndCurrentHome() {
        `when`(navController.invokeContainerBackPressed()).thenReturn(false)
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HOME)

        createViewModel()

        val backPressedResult = activityCallback.onBackPressedCommand!!.invoke()
        assertEquals(false, backPressedResult)
    }

    @Test
    fun onRestoreSavedStateAtHistoryTab() {
        var menuIds = MainBottomNavMenuType.MENU_HOME

        `when`(bottomNavigationViewUsecase.selectedItemId).then { menuIds }
        createViewModel()

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        menuIds = MainBottomNavMenuType.MENU_HISTORY
        activityCallback.onRestoreInstanceStateCommand?.invoke()

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navController, times(1)).navHome()
        verify(navController, times(1)).navHistory()
    }

    @Test
    fun onRestoreSavedStateAtHomeTab() {
        var menuIds = MainBottomNavMenuType.MENU_HOME

        `when`(bottomNavigationViewUsecase.selectedItemId).then { menuIds }
        createViewModel()

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        menuIds = MainBottomNavMenuType.MENU_HOME
        activityCallback.onRestoreInstanceStateCommand?.invoke()

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navController, times(1)).navHome()
    }
}