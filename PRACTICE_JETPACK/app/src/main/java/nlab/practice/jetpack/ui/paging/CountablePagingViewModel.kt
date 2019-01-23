package nlab.practice.jetpack.ui.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager.*
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        pagingItemRepository: PagingItemRepository,
        pagingManagerFactory: CountablePositionalPagingManager.Factory) {

    val listAdapter: BindingPagedListAdapter<PagingItemViewModel> = BindingPagedListAdapter.create()
    val recyclerViewConfig = RecyclerViewConfig()

    private val _pagingManager = pagingManagerFactory.create(pagingItemRepository)

    init {
        subscribePagedList()
        subscribeLoadErrorState()
    }

    private fun subscribePagedList() {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build()

        RxPagedListBuilder<Int, PagingItemViewModel>(createDataSourceFactory(), config)
                .buildObservable()
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun createDataSourceFactory(): DataSource.Factory<Int, PagingItemViewModel> {
        return object: DataSource.Factory<Int, PagingItemViewModel>() {
            override fun create(): DataSource<Int, PagingItemViewModel> {
                return _pagingManager.newDataSource().map { PagingItemViewModel(it) }
            }
        }
    }

    private fun subscribeLoadErrorState() {
        _pagingManager.stateSubject
                .filter { (it == DataLoadState.INIT_LOAD_ERROR) or (it == DataLoadState.LOAD_ERROR) }
                .doOnNext { _pagingManager.invalidate() }
                .subscribe()
                .addTo(_disposables)
    }

}