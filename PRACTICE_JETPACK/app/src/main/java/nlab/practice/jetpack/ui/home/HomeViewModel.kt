package nlab.practice.jetpack.ui.home

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.ui.main.ContainerFragmentCallback
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject

/**
 * HomeFragment 에 대한 ViewModel
 *
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        containerFragmentCallback: ContainerFragmentCallback,
        homeItemDecoration: HomeItemDecoration,
        private val _activityNavUsecase: ActivityNavUsecase,
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
        _homeHeaderViewModel.startTimer()
        headers.add(_homeHeaderViewModel)
        refreshItems()

        containerFragmentCallback.onBottomNavReselected(this::scrollToZeroIfEmptyChild)
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY) { _homeHeaderViewModel.stopTimer() }
    }

    private fun scrollToZeroIfEmptyChild(): Boolean {
        val isNeedScrollToZero = !_fragmentNavUsecase.hasChild()
        if (isNeedScrollToZero) {
            _recyclerViewUsecase.smoothScrollToPosition(0)
        }

        return isNeedScrollToZero
    }

    private fun createItems(): List<HomeItemViewModel> = listOf(
            createHomeAnkoFirstViewMenuViewModel(),
            createPagingTestMenuViewModel(),
            createListAdapterMenuViewModel(),
            createDragDropMenuViewModel()
    )

    private fun createHomeAnkoFirstViewMenuViewModel(): HomeItemViewModel = _testMenuRepository.getAnkoFirstViewMenu().let {
        _homeItemViewModelFactory.create(it) { _activityNavUsecase.startAnkoFistActivity() }
    }

    private fun createPagingTestMenuViewModel(): HomeItemViewModel = _testMenuRepository.getPagingTestMenu().let {
        _homeItemViewModelFactory.create(it) { _fragmentNavUsecase.navCountablePaging() }
    }

    private fun createListAdapterMenuViewModel(): HomeItemViewModel = _testMenuRepository.getListAdapterMenu().let {
        _homeItemViewModelFactory.create(it) { _fragmentNavUsecase.navListAdapterExample() }
    }

    private fun createDragDropMenuViewModel(): HomeItemViewModel = _testMenuRepository.getDragDropMenu().let {
        _homeItemViewModelFactory.create(it) { _fragmentNavUsecase.navDragDrop() }
    }

    private fun refreshItems() {
        items.clear()
        items.addAll(createItems())
    }
}