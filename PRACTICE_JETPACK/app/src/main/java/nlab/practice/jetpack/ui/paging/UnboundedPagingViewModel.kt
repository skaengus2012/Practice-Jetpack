package nlab.practice.jetpack.ui.paging

import android.util.Log
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
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager
import javax.inject.Inject
import kotlin.random.Random

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
        private val _toastHelper: ToastHelper,
        private val _schedulerFactory: SchedulerFactory,
        private val _imagePoolRepository: ImagePoolRepository,
        pagingManagerFactory: UnboundedPositionalPagingManager.Factory) : PagingViewModel {

    private val _subTitle = ObservableField<String>(_resourceProvider.getString(R.string.paging_unbounded_sub_title).toString())

    private val _isShowRefreshProgressBar = ObservableBoolean()

    private val _pagingManager = pagingManagerFactory.create(_pagingItemRepository)

    private val _listAdapter: BindingPagedListAdapter<PagingItemViewModel> = BindingPagedListAdapter()

    private val _singleScheduler = _schedulerFactory.single()

    init {
        subscribePagedList()
        subscribeLoadComplete()
    }

    private fun subscribePagedList() {
        val config = PagedList.Config.Builder()
                .setPageSize(100)
                .setPrefetchDistance(20)
                .setEnablePlaceholders(false)
                .build()

        RxPagedListBuilder<Int, PagingItemViewModel>(createDataSourceFactory(), config)
                .buildFlowable(BackpressureStrategy.BUFFER)
                .doOnNext { _listAdapter.submitList(it) }
                .observeOn(_schedulerFactory.ui())
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeLoadComplete() {
        _pagingManager.stateSubject
                .filter { it.state in listOf(
                        PositionalDataLoadState.INIT_LOAD_FINISH,
                        PositionalDataLoadState.LOAD_FINISH)
                }
                .observeOn(_schedulerFactory.ui())
                .doOnNext {
                    Log.d("dsa", "asdsad ${_listAdapter.itemCount}")
                }
                .subscribe()
                .addTo(_disposables)

    }

    override fun getListAdapter(): BindingPagedListAdapter<PagingItemViewModel> = _listAdapter

    override fun getSubTitle(): ObservableField<String> = _subTitle

    override fun isShowRefreshProgressBar(): ObservableBoolean = _isShowRefreshProgressBar

    override fun getBannerText(): String = _resourceProvider.getString(R.string.paging_banner_to_countable).toString()

    override fun refresh() {
        _isShowRefreshProgressBar.set(true)

        // NOTE
        // invalidate 호출 시, 잘못된 위치로 requestPosition 이 세팅되는 이슈 존재
        // 리프레쉬로 업데이트 시, 현재 pagedList 에 0을 지정해주도록 하자.
        _listAdapter.currentList?.loadAround(0)
        _toastHelper.showToast(R.string.paging_notify_refresh)

        _pagingManager.invalidate()
    }

    override fun addItems() {
        Single.fromCallable { addItemsInternal() }
                .subscribeOn(_singleScheduler)
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    _resourceProvider.getString(R.string.paging_add_item_unbounded_format, it.first, it.second).run {
                        _toastHelper.showToast(this)
                    }
                }
                .subscribe()
                .addTo(_disposables)
    }

    /**
     * Total 을 알 수 없다는 가정하에, 랜덤으로 데이터를 추가하는 로직 정의
     *
     * @return [시작하는 index, 추가된 아이템 개수] 가 출력
     */
    private fun addItemsInternal(): Pair<Int, Int> {
        val newItemCount = Random.nextInt(2, 50)
        val randomStartIndex = Random.nextInt(1500)

        val imageTotalSize = _imagePoolRepository.getSize()
        var startChoiceImage = Random.nextInt(imageTotalSize)
        var startIndex = randomStartIndex

        (0..newItemCount).map {
            val index = startIndex++
            val imageIndex = startChoiceImage++
            PagingItem(index, "New Instant Item - index : $index", _imagePoolRepository.get(imageIndex % imageTotalSize))
        }.run { _pagingItemRepository.addItems(this) }

        return Pair(randomStartIndex, newItemCount)
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