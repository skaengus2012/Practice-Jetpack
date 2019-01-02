package nlab.practice.jetpack.ui.home

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.databinding.DataBindingItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val _homeHeaderViewModel: HomeHeaderViewModel,
        private val _disposables: CompositeDisposable,
        private val _testMenuRepository: TestMenuRepository,
        private val _homeItemDecoration: HomeItemDecoration) {

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY) {
            _homeHeaderViewModel.stopTimer()
        }
    }

    fun getHeaderItems(): List<DataBindingItemViewModel> = listOf(_homeHeaderViewModel)

}