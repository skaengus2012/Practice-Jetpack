package nlab.practice.jetpack.ui.paging

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.PagingDataSourceRepository
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import javax.inject.Inject

/**
 * Paging 의 ViewModel 열람
 *
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _pagingDataSourceRepository: PagingDataSourceRepository,
        private val _schdulerFactory: SchedulerFactory) {

    init {

    }
}