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

package nlab.practice.jetpack.ui.home

import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.ui.main.ContainerFragmentCallback
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.nav.IntentProvider
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

private const val MOCK_TYPE_TEST_MENU_SLIDE                     = "SLIDE"
private const val MOCK_TYPE_TEST_MENU_ANKO                      = "ANKO"
private const val MOCK_TYPE_TEST_MENU_CENTER_SCROLL             = "CENTER_SCROLL"
private const val MOCK_TYPE_TEST_MENU_COLLAPSING_TOOLBAR        = "COLLAPSING_TOOLBAR"
private const val MOCK_TYPE_TEST_MENU_DRAG_N_DROP               = "DRAG_N_DROP"
private const val MOCK_TYPE_TEST_MENU_LIST_ADAPTER              = "LIST_ADAPTER"
private const val MOCK_TYPE_TEST_MENU_PAGING                    = "SLIDE"


/**
 * Test for HomeViewModel
 *
 * 유즈 케이스
 *
 * 1. 생명주기에 따라 Item 목록이 채워졌는지 확인 필요.
 * 2. Timer 시작 종료에 대한 처리 테스트 (타이머 자체는 따로 테스트 필요)
 * 3. 아이템 목록 추가에 대한 선택 액션 테스트
 * 4. BottomNavigation 에 의해 스크롤/ 자식뷰 제거가 되는지 확인
 * 5. RecyclerView 의 설정이 제대로 되어있는지 확인
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val lifecycleBinder = FragmentLifecycleBinder()

    private val containerFragmentCallback = ContainerFragmentCallback()

    @Mock
    lateinit var itemDecoration: HomeItemDecoration

    @Mock
    lateinit var headerViewModel: HomeHeaderViewModel

    @Mock
    lateinit var fragmentNavUsecase: FragmentNavUsecase

    @Mock
    lateinit var testMenuRepository: TestMenuRepository

    @Mock
    lateinit var recyclerViewUsecase: RecyclerViewUsecase

    @Mock
    lateinit var activityNavUsecase: ActivityNavUsecase

    @Mock
    lateinit var intentProvider: IntentProvider

    private lateinit var viewModel: HomeViewModel

    @Before
    fun initialize() {
        `when`(testMenuRepository.getSlideUpPanelExMenus()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_SLIDE))
        `when`(testMenuRepository.getAnkoFirstViewMenu()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_ANKO))
        `when`(testMenuRepository.getCenterScrollRecyclerView()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_CENTER_SCROLL))
        `when`(testMenuRepository.getCollapsingToolbarExMenu()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_COLLAPSING_TOOLBAR))
        `when`(testMenuRepository.getDragDropMenu()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_DRAG_N_DROP))
        `when`(testMenuRepository.getListAdapterMenu()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_LIST_ADAPTER))
        `when`(testMenuRepository.getPagingTestMenu()).thenReturn(createDummyTestMenu(MOCK_TYPE_TEST_MENU_PAGING))

        viewModel = HomeViewModel(
            lifecycleBinder,
            containerFragmentCallback,
            itemDecoration,
            headerViewModel,
            activityNavUsecase,
            intentProvider,
            fragmentNavUsecase,
            testMenuRepository,
            recyclerViewUsecase
        )
    }

    private fun createDummyTestMenu(type: String): TestMenu = TestMenu(type, 0, type)

    @Test
    fun startTimerOnViewCreated() {
        lifecycleBinder.apply(FragmentLifecycle.ON_VIEW_CREATED)
        verify(headerViewModel, times(1)).startTimer()
    }

    @Test
    fun stopTimberOnDestroyView() {
        lifecycleBinder.apply(FragmentLifecycle.ON_DESTROY_VIEW)
        verify(headerViewModel, times(1)).stopTimer()
    }

    @Test
    fun checkRecyclerViewDecorate() = with(viewModel.recyclerViewConfig) {
        assertNull(layoutManager)
        assert(itemTouchHelperSuppliers.isEmpty())
        assert(itemDecoration in itemDecorations)
    }

    @Test
    fun checkItems() {
        verify(testMenuRepository, times(1)).getSlideUpPanelExMenus()
        verify(testMenuRepository, times(1)).getAnkoFirstViewMenu()
        verify(testMenuRepository, times(1)).getCenterScrollRecyclerView()
        verify(testMenuRepository, times(1)).getCollapsingToolbarExMenu()
        verify(testMenuRepository, times(1)).getDragDropMenu()
        verify(testMenuRepository, times(1)).getListAdapterMenu()
        verify(testMenuRepository, times(1)).getPagingTestMenu()

        assertEquals(1, viewModel.headers.size)
        assertEquals(7, viewModel.items.size)
    }

    @Test
    fun onBottomNavigationReselectHasChild() {

    }

    @Test
    fun onBottomNavigationReselectEmptyChild() {

    }

}