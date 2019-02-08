package nlab.practice.jetpack.ui.listadapter

import androidx.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class ListAdapterExampleViewModel @Inject constructor(
        layoutManagerFactory: LayoutManagerFactory,
        itemDecoration: ListAdapterExampleItemDecoration,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _pagingItemRepository: PagingItemRepository,
        private val _listAdapterItemFactory: ListAdapterExampleItemViewModelFactory) : ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    private val _listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.create()

    private val _isShowErrorView = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val isShowRefreshProgressBar = ObservableBoolean(false)

    val recyclerViewConfig: RecyclerViewConfig

    init {
        initializeList()

        recyclerViewConfig = RecyclerViewConfig().apply {
            layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
            itemDecorations.add(itemDecoration)
        }
    }

    override fun isShowErrorView(): ObservableBoolean = _isShowErrorView

    override fun refresh() {
        _pagingItemRepository.getItems(0, 200)
                .subscribeOn(_schedulerFactory.io())
                .doOnSuccess {
                    list
                    ->
                    list.map { _listAdapterItemFactory.create(it) }.run { _listUpdateSubject.onNext(this) }
                }
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess { _isShowErrorView.set(false) }
                .doOnError { _isShowErrorView.set(true) }
                .doFinally {
                    if (isShowRefreshProgressBar.get()) {
                        isShowRefreshProgressBar.set(false)
                    }
                }
                .subscribe()
                .addTo(_disposables)
    }

    fun refreshBySwipeLayout() {
        isShowRefreshProgressBar.set(true)
        refresh()
    }

    private fun initializeList() {
        _listUpdateSubject
                .observeOn(_schedulerFactory.ui())
                .subscribe { listAdapter.submitList(it) }
                .addTo(_disposables)

        refresh()
    }
}