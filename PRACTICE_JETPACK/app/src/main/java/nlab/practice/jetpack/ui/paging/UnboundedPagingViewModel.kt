package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
class UnboundedPagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _pagingItemRepository: PagingItemRepository,
        private val _pagingItemViewModelFactory: PagingItemViewModelFactory,
        private val _activityCommonUsecase: ActivityCommonUsecase,
        private val _resourceProvider: ResourceProvider,
        pagingManagerFactory: UnboundedPositionalPagingManager.Factory) : PagingViewModel {

    private val _subTitle = ObservableField<String>(_resourceProvider.getString(R.string.paging_unbounded_sub_title).toString())
    private val _isShowRefreshProgressBar = ObservableBoolean()

    private val _pagingManager = pagingManagerFactory.create(_pagingItemRepository)

    private val _listAdapter: BindingPagedListAdapter<PagingItemViewModel> = BindingPagedListAdapter.create()

    init {
        subscribePagedList()
    }

    private fun subscribePagedList() {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(100)
                .setPageSize(100)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(false)
                .build()

        RxPagedListBuilder<Int, PagingItemViewModel>(createDataSourceFactory(), config)
                .buildFlowable(BackpressureStrategy.BUFFER)
                .doOnNext { _listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposables)
    }

    override fun getListAdapter(): BindingPagedListAdapter<PagingItemViewModel> = _listAdapter

    override fun getSubTitle(): ObservableField<String> = _subTitle

    override fun isShowRefreshProgressBar(): ObservableBoolean = _isShowRefreshProgressBar

    override fun refresh() {

    }

    override fun addItems() {

    }

    override fun onClickBackButton() {
        _activityCommonUsecase.onBackPressed()
    }

    private fun createDataSourceFactory(): DFactory = object: DFactory() {

        override fun create(): DataSource<Int, PagingItemViewModel> {
            _isShowRefreshProgressBar.set(false)
            return _pagingManager.newDataSource().map { createPagingItemViewModel(it) }
        }

        fun createPagingItemViewModel(pagingItem: PagingItem): PagingItemViewModel = _pagingItemViewModelFactory.create(pagingItem) {
            it.navCountablePaging()
        }
    }

}