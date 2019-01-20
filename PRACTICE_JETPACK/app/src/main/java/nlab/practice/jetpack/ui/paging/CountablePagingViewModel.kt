package nlab.practice.jetpack.ui.paging

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _schdulerFactory: SchedulerFactory,
        pagingItemRepository: PagingItemRepository,
        pagingManagerFactory: CountablePositionalPagingManager.Factory) {

    private val pagingManager: CountablePositionalPagingManager<PagingItem>

    init {
        pagingManager = pagingManagerFactory.create(pagingItemRepository)
    }
}