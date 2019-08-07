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
import nlab.practice.jetpack.anyNonNull
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
    lateinit var containerUsecase: PrimaryContainerUsecase

    @Mock
    lateinit var navUsecase: MainNavUsecase

    @Mock
    lateinit var bottomNavigationViewUsecase: MainBottomNavigationViewUsecase

    private lateinit var viewModel: MainHolderViewModel

    @Before
    fun initialize() {
        `when`(bottomNavigationViewUsecase.onSelected(anyNonNull())).thenReturn(selectTabSubject)
        `when`(bottomNavigationViewUsecase.onReSelected()).thenReturn(reSelectTabSubject)

        viewModel = MainHolderViewModel(
            lifecycleBinder,
            activityCallback,
            bottomNavigationViewUsecase,
            containerUsecase,
            navUsecase
        )
    }

    @Test
    fun when_OnCreatedAndFirst_expected_NeverChangedBottomNavMenuAndShouldMoveHome() {
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HOME)

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navUsecase, times(1)).navHome()
    }

    @Test
    fun when_TabBottomNavMenu_expected_MoveValidPage() {
        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HISTORY)
        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)
        selectTabSubject.onNext(MainBottomNavMenuType.MENU_HISTORY)

        verify(navUsecase, times(1)).navHome()
        verify(navUsecase, times(2)).navHistory()
    }

    @Test
    fun when_EmptyChildrenAndReselectedHome_expected_InvokeContainerReSelectedAndNeverClearChildren() {
        `when`(containerUsecase.invokeContainerReselected()).thenReturn(true)

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        reSelectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)

        verify(containerUsecase, times(1)).invokeContainerReselected()
        verify(containerUsecase, never()).clearContainerChildren()
    }

    @Test
    fun when_HasChildrenAndReselectedHome_expected_InvokeContainerReSelectedAndClearChildren() {
        `when`(containerUsecase.invokeContainerReselected()).thenReturn(false)

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        reSelectTabSubject.onNext(MainBottomNavMenuType.MENU_HOME)

        verify(containerUsecase, times(1)).invokeContainerReselected()
        verify(containerUsecase, times(1)).clearContainerChildren()
    }

    @Test
    fun when_OnBackPressedAndResultTrue_expected_NeverGoHome() {
        `when`(containerUsecase.invokeContainerBackPressed()).thenReturn(true)

        assertEquals(true, activityCallback.onBackPressedCommand!!.invoke())
        verify(containerUsecase, times(1)).invokeContainerBackPressed()
        verify(bottomNavigationViewUsecase, never()).selectedItemId
        verify(navUsecase, never()).navHome()
    }

    @Test
    fun when_OnBackPressedAndResultFalseAndCurrentNotHome_expected_GoHome() {
        `when`(containerUsecase.invokeContainerBackPressed()).thenReturn(false)
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HISTORY)

        assertEquals(true, activityCallback.onBackPressedCommand!!.invoke())
        verify(bottomNavigationViewUsecase, times(1)).selectedItemId
        verify(bottomNavigationViewUsecase, times(1)).selectedItemId = MainBottomNavMenuType.MENU_HOME
    }

    @Test
    fun when_OnBackPressedAndResultFalseAndCurrentHome_expected_CloseActivity() {
        `when`(containerUsecase.invokeContainerBackPressed()).thenReturn(false)
        `when`(bottomNavigationViewUsecase.selectedItemId).thenReturn(MainBottomNavMenuType.MENU_HOME)

        assertEquals(false, activityCallback.onBackPressedCommand!!.invoke())
    }

    @Test
    fun when_OnRestoredAndLatestNotHomeTab_expected_GoHistory() {
        var menuIds = MainBottomNavMenuType.MENU_HOME

        `when`(bottomNavigationViewUsecase.selectedItemId).then { menuIds }

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        menuIds = MainBottomNavMenuType.MENU_HISTORY
        activityCallback.onRestoreInstanceStateCommand?.invoke()

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navUsecase, times(1)).navHome()
        verify(navUsecase, times(1)).navHistory()
    }

    @Test
    fun when_OnRestoredAndLatestHomeTab_expected_NotMoved() {
        var menuIds = MainBottomNavMenuType.MENU_HOME

        `when`(bottomNavigationViewUsecase.selectedItemId).then { menuIds }

        lifecycleBinder.apply(ActivityLifecycle.ON_CREATE)

        menuIds = MainBottomNavMenuType.MENU_HOME
        activityCallback.onRestoreInstanceStateCommand?.invoke()

        verify(bottomNavigationViewUsecase, never()).selectedItemId = anyInt()
        verify(navUsecase, times(1)).navHome()
    }
}