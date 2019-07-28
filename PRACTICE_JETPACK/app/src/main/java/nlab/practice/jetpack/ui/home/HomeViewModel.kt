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

import androidx.databinding.ObservableArrayList
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.ui.collapsingtoolbar.CollapsingToolbarActivity
import nlab.practice.jetpack.ui.landscape.LandScapeActivity
import nlab.practice.jetpack.ui.main.ContainerFragmentCallback
import nlab.practice.jetpack.ui.slide.SlideUpSampleActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.nav.*
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject
import javax.inject.Named

/**
 * HomeFragment 에 대한 ViewModel
 *
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
    fragmentLifeCycleBinder: FragmentLifeCycleBinder,
    containerFragmentCallback: ContainerFragmentCallback,
    homeItemDecoration: HomeItemDecoration,
    homeHeaderViewModel: HomeHeaderViewModel,
    @Named(ContextInjectionType.ACTIVITY) private val activityNavUsecase: ActivityNavUsecase,
    @Named(ContextInjectionType.ACTIVITY) private val intentProvider: IntentProvider,
    private val fragmentNavUsecase: FragmentNavUsecase,
    private val homeItemViewModelFactory: HomeItemViewModelFactory,
    private val testMenuRepository: TestMenuRepository,
    private val recyclerViewUsecase: RecyclerViewUsecase
) {

    val headers = ObservableArrayList<HomeHeaderViewModel>()

    val items = ObservableArrayList<HomeItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        itemDecorations.add(homeItemDecoration)
    }

    private val disposables = CompositeDisposable()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            homeHeaderViewModel.startTimer()
        }

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY_VIEW) {
            disposables.clear()
            homeHeaderViewModel.stopTimer()
        }

        containerFragmentCallback.onBottomNavReselected(this::scrollToZeroIfEmptyChild)

        headers.add(homeHeaderViewModel)
        refreshItems()
    }

    private fun scrollToZeroIfEmptyChild(): Boolean {
        val isNeedScrollToZero = !fragmentNavUsecase.hasChild()
        if (isNeedScrollToZero) {
            recyclerViewUsecase.smoothScrollToPosition(0)
        }

        return isNeedScrollToZero
    }

    private fun createItems(): List<HomeItemViewModel> = listOf(
        createAnkoFirstViewMenu(),
        createPagingTestMenu(),
        createListAdapterMenu(),
        createDragDropMenu(),
        createCollapsingToolbarExMenu(),
        createSlideUpPanelExMenu(),
        createCenterScrollExMenu(),
        createLandScapeModeMenu()
    )

    private fun createViewModel(testMenu: TestMenu, onClickAction: () -> Unit): HomeItemViewModel {
        return homeItemViewModelFactory.create(testMenu, onClickAction)
    }

    private fun createAnkoFirstViewMenu() = createViewModel(testMenuRepository.getAnkoFirstViewMenu()) {
        activityNavUsecase.startActivity<AnkoFirstActivity>(intentProvider)
    }

    private fun createPagingTestMenu() = createViewModel(testMenuRepository.getPagingTestMenu()) {
        fragmentNavUsecase.navCountablePaging()
    }

    private fun createListAdapterMenu() = createViewModel(testMenuRepository.getListAdapterMenu()) {
        fragmentNavUsecase.navListAdapterExample()
    }

    private fun createDragDropMenu() = createViewModel(testMenuRepository.getDragDropMenu()) {
        fragmentNavUsecase.navDragDrop()
    }

    private fun createCollapsingToolbarExMenu() = createViewModel(testMenuRepository.getCollapsingToolbarExMenu()) {
        activityNavUsecase.startActivity<CollapsingToolbarActivity>(intentProvider)
    }

    private fun createSlideUpPanelExMenu() = createViewModel(testMenuRepository.getSlideUpPanelExMenus()) {
        activityNavUsecase.startActivity<SlideUpSampleActivity>(intentProvider)
    }

    private fun createCenterScrollExMenu() = createViewModel(testMenuRepository.getCenterScrollRecyclerView()) {
        fragmentNavUsecase.navCenterScrolling()
    }

    private fun createLandScapeModeMenu() = createViewModel(testMenuRepository.getLandScapeModeMenu()) {
        activityNavUsecase.startActivity<LandScapeActivity>(intentProvider)
    }

    private fun refreshItems() {
        items.clear()
        items.addAll(createItems())
    }
}