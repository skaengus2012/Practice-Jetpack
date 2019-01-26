package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.ImagePoolRepository
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _activityCommonUsecase: ActivityCommonUsecase,
        private val _pagingItemRepository: PagingItemRepository,
        private val _imagePoolRepository: ImagePoolRepository,
        private val _schedulerFactory: SchedulerFactory,
        private val _toastHelper: ToastHelper,
        private val _resourceProvider: ResourceProvider,
        private val _recyclerViewUsecase: RecyclerViewUsecase,
        pagingManagerFactory: CountablePositionalPagingManager.Factory) {

    val listAdapter: BindingPagedListAdapter<PagingItemViewModel> =
            BindingPagedListAdapter.create(placeholderResId = R.layout.view_paging_item_placeholder)

    val recyclerViewConfig = RecyclerViewConfig()

    val isRefreshing = ObservableBoolean(false)
    private var _isRefreshLock = false

    val subTitle = ObservableField<String>()
    private val _subTitleFormat = _resourceProvider.getString(R.string.paging_countable_sub_title_format)

    private val _pagingManager = pagingManagerFactory.create(_pagingItemRepository)
    private val _singleScheduler = _schedulerFactory.single()

    init {
        loadTitle()
        subscribePagedList()
        subscribeTotalCountChanged()
        subscribeLoadFinish()
        subscribeLoadError()
    }

    fun onClickBackButton() = _activityCommonUsecase.onBackPressed()

    private fun subscribePagedList() {
        // NOTE : placeHolder 는 아이템의 전체 개수를 알고 있을 때만 사용 가능
        //        이 곳에서는 사용하고 있으며 이는 에러가 발생했을 때, 오직 invalidate 를 통해서만 처리 가능
        //        invalidate 를 하지 않으면, placeHolder 가 사라지지 않음 -> 이 때는 여러 조건이 필요할 순 있음
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(100)
                .setPageSize(100)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(true)
                .build()

        RxPagedListBuilder<Int, PagingItemViewModel>(createDataSourceFactory(), config)
                .buildFlowable(BackpressureStrategy.BUFFER)
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeTotalCountChanged() {
        _pagingManager.stateSubject
                .filter {
                    it.state in listOf(
                        PositionalDataLoadState.LOAD_DATA_SIZE_CHANGED,
                        PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED) }
                .observeOn(_schedulerFactory.ui())
                .doOnNext {
                    _toastHelper.showToast(R.string.paging_notify_data_size_changed)
                    _pagingManager.invalidate()
                }
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeLoadError() {
        _pagingManager.stateSubject
                .observeOn(_schedulerFactory.ui())
                .filter { it.state in listOf(
                        PositionalDataLoadState.LOAD_ERROR,
                        PositionalDataLoadState.INIT_LOAD_ERROR) }
                .filter { _isRefreshLock }
                .doOnNext {
                    _pagingManager.invalidate()
                    _isRefreshLock = false
                }
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeLoadFinish() {
        _pagingManager.stateSubject
                .observeOn(_schedulerFactory.ui())
                .filter { it.state == PositionalDataLoadState.LOAD_FINISH }
                .filter { _isRefreshLock }
                .doOnNext {
                    _recyclerViewUsecase.scrollToPositionWithOffset(0,0)
                    _isRefreshLock = false
                }
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

    fun addItems() {
        _pagingItemRepository.getTotalCount()
                .flatMap{ getItemsAddingItemCountSingle(it) }
                .subscribeOn(_singleScheduler)
                .observeOn(_schedulerFactory.ui())
                .map { _resourceProvider.getString(R.string.paging_add_item, it) }
                .doOnSuccess { _toastHelper.showToast(it) }
                .doOnSuccess { loadTitle() }
                .subscribe()
                .addTo(_disposables)
    }

    fun refresh() {
        _isRefreshLock = true
        isRefreshing.set(true)
        _toastHelper.showToast(R.string.paging_notify_refresh)
        _pagingManager.invalidate()
    }

    private fun getItemsAddingItemCountSingle(totalSize: Int): Single<Int> = Single.fromCallable {
        val newItemCount = Random.nextInt(2, 30)

        val imageTotalSize = _imagePoolRepository.getSize()
        var startChoiceImage = Random.nextInt(imageTotalSize)

        var startIndex = totalSize

        (0..newItemCount).map {
            val index = startIndex++
            val imageIndex = startChoiceImage++
            PagingItem(index, "New Instant Item - index : $index", _imagePoolRepository.get(imageIndex % imageTotalSize))
        }.run { _pagingItemRepository.addItems(this) }

        newItemCount
    }

    private fun createDataSourceFactory(): DataSource.Factory<Int, PagingItemViewModel> {
        return object: DataSource.Factory<Int, PagingItemViewModel>() {
            override fun create(): DataSource<Int, PagingItemViewModel> {
                isRefreshing.set(false)
                return _pagingManager.newDataSource().map { PagingItemViewModel(it) }
            }
        }
    }
}