package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableField
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _activityCommonUsecase: ActivityCommonUsecase,
        private val _pagingItemRepository: PagingItemRepository,
        private val _schedulerFactory: SchedulerFactory,
        resourceProvider: ResourceProvider,
        pagingManagerFactory: CountablePositionalPagingManager.Factory) {

    val listAdapter: BindingPagedListAdapter<PagingItemViewModel> = BindingPagedListAdapter.create()
    val recyclerViewConfig = RecyclerViewConfig()
    val subTitle = ObservableField<String>()
    private val _subTitleFormat = resourceProvider.getString(R.string.paging_countable_sub_title_format)

    private val _pagingManager = pagingManagerFactory.create(_pagingItemRepository)

    init {
        loadTitle()
        subscribePagedList()
     //   subscribeLoadErrorState()
    }

    fun onClickBackButton() = _activityCommonUsecase.onBackPressed()

    private fun subscribePagedList() {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(100)
                .setPageSize(100)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build()

        RxPagedListBuilder<Int, PagingItemViewModel>(createDataSourceFactory(), config)
                .buildObservable()
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun loadTitle() {
        _pagingItemRepository.getTotalCount()
                .subscribeOn(_schedulerFactory.io())
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess { String.format(_subTitleFormat.toString(), it).run { subTitle.set(this) } }
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
}