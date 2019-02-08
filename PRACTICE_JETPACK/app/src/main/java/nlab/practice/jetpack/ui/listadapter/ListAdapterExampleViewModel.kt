package nlab.practice.jetpack.ui.listadapter

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.selection.SelectionEvent
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class ListAdapterExampleViewModel @Inject constructor(
        layoutManagerFactory: LayoutManagerFactory,
        itemDecoration: ListAdapterExampleItemDecoration,
        private val _selectionTrackerUsecase: SelectionTrackerUsecase,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _pagingItemRepository: PagingItemRepository,
        private val _listAdapterItemFactory: ListAdapterExampleItemViewModelFactory) : ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    private val _listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.create()

    private val _isShowErrorView = ObservableBoolean(false)

    val isSelectMode = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val isShowRefreshProgressBar = ObservableBoolean(false)

    val selectCountText = ObservableField<String>()

    val recyclerViewConfig: RecyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
        itemDecorations.add(itemDecoration)
    }

    init {
        initializeList()
        subscribeSelection()
    }

    override fun isShowErrorView(): ObservableBoolean = _isShowErrorView

    override fun refresh() {
        _pagingItemRepository.getItems(0, 200)
                .subscribeOn(_schedulerFactory.io())
                .doOnSuccess {
                    it.map { item -> _listAdapterItemFactory.create(item) }.run { _listUpdateSubject.onNext(this) }
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
                .subscribe {
                    listAdapter.submitList(it)
                    _selectionTrackerUsecase.replaceList(it)
                }
                .addTo(_disposables)

        refresh()
    }

    private fun subscribeSelection() {
        _selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.SELECTION_CHANGED }
                .doOnNext {
                    if (!isSelectMode.get()) {
                        isSelectMode.set(true)
                        updateSelectCountText()
                    }
                }
                .subscribe()
                .addTo(_disposables)

        _selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.STATE_CHANGED }
                .doOnNext { updateSelectCountText() }
                .subscribe()
                .addTo(_disposables)
    }

    fun clearSelectState() {
        _selectionTrackerUsecase.getSelectionTracker()?.clearSelection()
        isSelectMode.set(false)
    }

    private fun updateSelectCountText() {
        val selectionSize = _selectionTrackerUsecase.getSelectionTracker()?.selection?.size() ?: 0
        val totalSize = _listUpdateSubject.value?.size ?: 0

        selectCountText.set("$selectionSize / $totalSize")
    }
}