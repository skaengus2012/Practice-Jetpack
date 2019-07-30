/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import nlab.practice.jetpack.util.di.activity.ActivityCommonUsecase
import nlab.practice.jetpack.util.di.fragment.FragmentCallback
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
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
    fragmentLifeCycleBinder: FragmentLifecycleBinder,
    private val selectionTrackerUsecase: SelectionTrackerUsecase,
    private val schedulerFactory: SchedulerFactory,
    private val pagingItemRepository: PagingItemRepository,
    private val listAdapterItemFactory: ListAdapterExampleItemViewModelFactory,
    private val activityCommonUsecase: ActivityCommonUsecase,
    private val toastHelper: ToastHelper,
    private val snackBarHelper: SnackBarHelper
) : ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    private val disposables = CompositeDisposable()

    private val listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.create()

    private val isShowErrorView = ObservableBoolean(false)

    val isSelectMode = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val isShowRefreshProgressBar = ObservableBoolean(false)

    val selectCountText = ObservableField<String>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
        itemDecorations.add(itemDecoration)
    }

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifecycle.ON_VIEW_CREATED) {
            initializeList()
            subscribeSelection()
        }

        fragmentLifeCycleBinder.bindUntil(FragmentLifecycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }

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

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    override fun refresh() {
        pagingItemRepository.getItems(0, 200)
                .subscribeOn(schedulerFactory.io())
                .doOnSuccess {
                    it.map { item -> listAdapterItemFactory.create(item) }.run { listUpdateSubject.onNext(this) }
                }
                .observeOn(schedulerFactory.ui())
                .doOnSuccess { isShowErrorView.set(false) }
                .doOnError {
                    isShowErrorView.set(true)
                    clearSelectState()
                }
                .doFinally {
                    if (isShowRefreshProgressBar.get()) {
                        isShowRefreshProgressBar.set(false)
                    }
                }
                .subscribe()
                .addTo(disposables)
    }

    fun refreshBySwipeLayout() {
        isShowRefreshProgressBar.set(true)
        refresh()
    }

    private fun initializeList() {
        listUpdateSubject
                .observeOn(schedulerFactory.ui())
                .doOnNext {
                    listAdapter.submitList(it)
                    selectionTrackerUsecase.replaceList(it)
                }
                .subscribe()
                .addTo(disposables)

        refresh()
    }

    private fun subscribeSelection() {
        selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.SELECTION_CHANGED }
                .doOnNext {
                    when {
                        // 현재 Selection 이 없다면 선택모드를 종료한다. 구글가이드임..
                        !(selectionTrackerUsecase.getSelectionTracker()?.hasSelection()?: false) -> {
                            if (isSelectMode.get()) {
                                clearSelectState()
                            }
                        }

                        !isSelectMode.get() -> isSelectMode.set(true)
                    }
                }
                .subscribe()
                .addTo(disposables)

        selectionTrackerUsecase.getSelectionEventSubject()
                .filter { it.eventCode == SelectionEvent.Code.STATE_CHANGED }
                .doOnNext {
                    updateSelectCountText()

                    listUpdateSubject.value
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
                .addTo(disposables)
    }

    fun clearSelectState() {
        isSelectMode.set(false)
        selectionTrackerUsecase.getSelectionTracker()?.clearSelection()
    }

    fun removeSelectedItem() {
        val currentSelection =  selectionTrackerUsecase.getSelectionTracker()?.selection

        listUpdateSubject.value
                ?.filter {
                    // 포함되지 않은 목록만 살린다.
                    val isRemoveItem = currentSelection?.contains(it.getSelectionKey()) ?: false
                    !isRemoveItem
                }
                ?.run { listUpdateSubject.onNext(this) }

        clearSelectState()
        updateSelectCountText()

        snackBarHelper.showSnackBar(
                message = R.string.listadapter_remove_message,
                duration = Snackbar.LENGTH_LONG,
                actionMessage = R.string.listadapter_action_message,
                actionBehavior = { toastHelper.showToast(R.string.listadapter_goodbye_snack_bar) })
    }

    private fun updateSelectCountText() {
        val selectionSize = selectionTrackerUsecase.getSelectionTracker()?.selection?.size() ?: 0
        val totalSize = listUpdateSubject.value?.size ?: 0

        selectCountText.set("$selectionSize / $totalSize")
    }

    fun onBackPressed() {
        activityCommonUsecase.onBackPressed()
    }
}