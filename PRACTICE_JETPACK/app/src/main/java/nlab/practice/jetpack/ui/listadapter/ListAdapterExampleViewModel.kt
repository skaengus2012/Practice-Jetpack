package nlab.practice.jetpack.ui.listadapter

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.FragmentCallback
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
        fragmentCallback: FragmentCallback,
        private val _selectionTrackerUsecase: SelectionTrackerUsecase,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _pagingItemRepository: PagingItemRepository,
        private val _listAdapterItemFactory: ListAdapterExampleItemViewModelFactory,
        private val _activityCommonUsecase: ActivityCommonUsecase,
        private val _toastHelper: ToastHelper,
        private val _snackBarHelper: SnackBarHelper) : ListErrorPageViewModel {

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

        fragmentCallback.onBackPressed {
            // 현재 선택모드일 경우, 백키를 누른다면 이전상태로 돌린다.
            if (isSelectMode.get()) {
                clearSelectState()
                true
            } else {
                false
            }
        }
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
                .doOnError {
                    _isShowErrorView.set(true)
                    clearSelectState()
                }
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
                .doOnNext {
                    listAdapter.submitList(it)
                    _selectionTrackerUsecase.replaceList(it)
                }
                .subscribe()
                .addTo(_disposables)

        refresh()
    }

    private fun subscribeSelection() {
        _selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.SELECTION_CHANGED }
                .doOnNext {
                    when {
                        // 현재 Selection 이 없다면 선택모드를 종료한다. 구글가이드임..
                        !(_selectionTrackerUsecase.getSelectionTracker()?.hasSelection()?: false) -> {
                            if (isSelectMode.get()) {
                                clearSelectState()
                            }
                        }

                        !isSelectMode.get() -> isSelectMode.set(true)
                    }
                }
                .subscribe()
                .addTo(_disposables)

        _selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.STATE_CHANGED }
                .doOnNext {
                    updateSelectCountText()

                    _listUpdateSubject.value
                            ?.filter {
                                viewModel
                                ->
                                viewModel.getSelectionKey() == it.key
                            }?.forEach {
                                viewModel
                                ->
                                it.selected?.run { viewModel.selectState = this }
                            }
                }
                .subscribe()
                .addTo(_disposables)
    }

    fun clearSelectState() {
        isSelectMode.set(false)
        _selectionTrackerUsecase.getSelectionTracker()?.clearSelection()
    }

    fun removeSelectedItem() {
        val currentSelection =  _selectionTrackerUsecase.getSelectionTracker()?.selection

        _listUpdateSubject.value
                ?.filter {
                    // 포함되지 않은 목록만 살린다.
                    val isRemoveItem = currentSelection?.contains(it.getSelectionKey()) ?: false
                    !isRemoveItem
                }
                ?.run { _listUpdateSubject.onNext(this) }

        clearSelectState()
        updateSelectCountText()

        _snackBarHelper.showSnackBar(
                message = R.string.listadapter_remove_message,
                duration = Snackbar.LENGTH_LONG,
                actionMessage = R.string.listadapter_action_message,
                actionBehavior = { _toastHelper.showToast(R.string.listadapter_goodbye_snack_bar) })
    }

    private fun updateSelectCountText() {
        val selectionSize = _selectionTrackerUsecase.getSelectionTracker()?.selection?.size() ?: 0
        val totalSize = _listUpdateSubject.value?.size ?: 0

        selectCountText.set("$selectionSize / $totalSize")
    }

    fun onBackPressed() {
        _activityCommonUsecase.onBackPressed()
    }
}