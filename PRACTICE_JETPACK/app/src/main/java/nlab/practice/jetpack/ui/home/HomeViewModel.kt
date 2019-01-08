package nlab.practice.jetpack.ui.home

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import javax.inject.Inject

/**
 * HomeFragment 에 대한 ViewModel
 *
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        homeItemDecoration: HomeItemDecoration,
        private val _activityNavUsecase: ActivityNavUsecase,
        private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _homeHeaderViewModel: HomeHeaderViewModel,
        private val _homeItemViewModelFactory: HomeItemViewModelFactory,
        private val _testMenuRepository: TestMenuRepository) {

    val headers = ObservableArrayList<HomeHeaderViewModel>()

    val items = ObservableArrayList<HomeItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        itemDecorations.add(homeItemDecoration)
    }

    init {
        _homeHeaderViewModel.startTimer()
        headers.add(_homeHeaderViewModel)
        refreshItems()

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY) { _homeHeaderViewModel.stopTimer() }
    }

    private fun createItems(): List<HomeItemViewModel> = listOf(
            createHomeAnkoFirstViewMenuViewModel(),
            createPagingTestMenuViewModel()
    )

    private fun createHomeAnkoFirstViewMenuViewModel(): HomeItemViewModel = _testMenuRepository.getAnkoFirstViewMenu().let {
        _homeItemViewModelFactory.create(it) { _activityNavUsecase.startAnkoFistActivity() }
    }

    private fun createPagingTestMenuViewModel(): HomeItemViewModel = _testMenuRepository.getPagingTestMenu().let {
        _homeItemViewModelFactory.create(it) { _fragmentNavUsecase.navPaging() }
    }

    private fun refreshItems() {
        items.clear()
        items.addAll(createItems())
    }


}