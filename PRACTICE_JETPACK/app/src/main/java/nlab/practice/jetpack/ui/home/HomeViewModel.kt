package nlab.practice.jetpack.ui.home

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.TestMenuRepository
import javax.inject.Inject

/**
 * @author Doohyun
 */
class HomeViewModel @Inject constructor(
        private val _diposables: CompositeDisposable,
        private val _testMenuRepository: TestMenuRepository,
        private val _homeItemDecoration: HomeItemDecoration) {

}