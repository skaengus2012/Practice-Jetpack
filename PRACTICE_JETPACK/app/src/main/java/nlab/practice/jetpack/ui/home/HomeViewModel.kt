package nlab.practice.jetpack.ui.home

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.databinding.RecyclerViewConfig
import javax.inject.Inject

/**
 * HomeFragment 에 대한 ViewModel
 *
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        homeItemDecoration: HomeItemDecoration,
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

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_ACTIVITY_CREATED) { loadItems() }
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY) { _homeHeaderViewModel.stopTimer() }
    }

    private fun loadItems() {
        headers.add(_homeHeaderViewModel)
        items.addAll(createItems())
    }

    private fun createItems(): List<HomeItemViewModel> = listOf(
            createHomeAnkoFirstViewMenuViewModel(),
            createPagingTestMenuViewModel()
    )

    private fun createHomeAnkoFirstViewMenuViewModel(): HomeItemViewModel = _testMenuRepository.getAnkoFirstViewMenu().let {
        _homeItemViewModelFactory.create(it, R.id.nav_anko_first_activity)
    }

    private fun createPagingTestMenuViewModel(): HomeItemViewModel = _testMenuRepository.getPagingTestMenu().let {
        _homeItemViewModelFactory.create(it, R.id.nav_paging_test_fragment)
    }


}