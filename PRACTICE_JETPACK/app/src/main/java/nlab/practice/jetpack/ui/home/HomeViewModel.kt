package nlab.practice.jetpack.ui.home

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.ui.collapsingtoolbar.CollapsingToolbarActivity
import nlab.practice.jetpack.ui.main.ContainerFragmentCallback
import nlab.practice.jetpack.ui.slide.SlideUpSampleActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
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
        @Named(ContextInjectionType.ACTIVITY) private val _activityNavUsecase: ActivityNavUsecase,
        @Named(ContextInjectionType.ACTIVITY) private val _intentProvider: IntentProvider,
        private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _homeHeaderViewModel: HomeHeaderViewModel,
        private val _homeItemViewModelFactory: HomeItemViewModelFactory,
        private val _testMenuRepository: TestMenuRepository,
        private val _recyclerViewUsecase: RecyclerViewUsecase) {

    val headers = ObservableArrayList<HomeHeaderViewModel>()

    val items = ObservableArrayList<HomeItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        itemDecorations.add(homeItemDecoration)
    }

    init {
        headers.add(_homeHeaderViewModel)
        initializeTimer(fragmentLifeCycleBinder)
        refreshItems()

        containerFragmentCallback.onBottomNavReselected(this::scrollToZeroIfEmptyChild)
    }

    private fun initializeTimer(fragmentLifeCycleBinder: FragmentLifeCycleBinder) {
        _homeHeaderViewModel.startTimer()

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY_VIEW) {
            _homeHeaderViewModel.stopTimer()
        }
    }

    private fun scrollToZeroIfEmptyChild(): Boolean {
        val isNeedScrollToZero = !_fragmentNavUsecase.hasChild()
        if (isNeedScrollToZero) {
            _recyclerViewUsecase.smoothScrollToPosition(0)
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
            createCenterScrollExMenu()
    )

    private fun createViewModel(testMenu: TestMenu, onClickAction: () -> Unit): HomeItemViewModel {
        return _homeItemViewModelFactory.create(testMenu, onClickAction)
    }

    private fun createAnkoFirstViewMenu() = createViewModel(_testMenuRepository.getAnkoFirstViewMenu()) {
        _activityNavUsecase.startActivity<AnkoFirstActivity>(_intentProvider)
    }

    private fun createPagingTestMenu() = createViewModel(_testMenuRepository.getPagingTestMenu()) {
        _fragmentNavUsecase.navCountablePaging()
    }

    private fun createListAdapterMenu() = createViewModel(_testMenuRepository.getListAdapterMenu()) {
        _fragmentNavUsecase.navListAdapterExample()
    }

    private fun createDragDropMenu() = createViewModel(_testMenuRepository.getDragDropMenu()) {
        _fragmentNavUsecase.navDragDrop()
    }

    private fun createCollapsingToolbarExMenu() = createViewModel(_testMenuRepository.getCollapsingToolbarExMenu()) {
        _activityNavUsecase.startActivity<CollapsingToolbarActivity>(_intentProvider)
    }

    private fun createSlideUpPanelExMenu() = createViewModel(_testMenuRepository.getSlideUpPanelExMenus()) {
        _activityNavUsecase.startActivity<SlideUpSampleActivity>(_intentProvider)
    }

    private fun createCenterScrollExMenu() = createViewModel(_testMenuRepository.getCenterScrollRecyclerView()) {
        _fragmentNavUsecase.navCenterScrolling()
    }

    private fun refreshItems() {
        items.clear()
        items.addAll(createItems())
    }
}