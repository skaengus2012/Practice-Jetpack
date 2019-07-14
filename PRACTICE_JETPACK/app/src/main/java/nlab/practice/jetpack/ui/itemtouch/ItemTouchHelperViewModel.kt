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

package nlab.practice.jetpack.ui.itemtouch

import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.ItemTouchHelper
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.touch.SwipeDeleteTouchEventHelperCallback
import nlab.practice.jetpack.util.recyclerview.touch.VerticalDragItemTouchHelperCallback
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
class ItemTouchHelperViewModel @Inject constructor(
    fragmentLifeCycleBinder: FragmentLifeCycleBinder,
    private val activityCommonUsecase: ActivityCommonUsecase,
    private val pagingItemRepository: PagingItemRepository,
    private val schedulerFactory: SchedulerFactory,
    private val dragCallback: VerticalDragItemTouchHelperCallback,
    private val swipeCallback: SwipeDeleteTouchEventHelperCallback,
    private val touchHelpers: Set<@JvmSuppressWildcards ItemTouchHelper>,
    private val itemModelFactory: ItemTouchHelperItemViewModelFactory
) : ListErrorPageViewModel {

    private val itemUpdateSubject = BehaviorSubject.createDefault<List<ItemTouchHelperItemViewModel>>(emptyList())

    private val isShowErrorView = ObservableBoolean(false)

    private val disposables = CompositeDisposable()

    val listAdapter = BindingItemListAdapter<ItemTouchHelperItemViewModel>()

    val recyclerViewConfig = createRecyclerViewConfig()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            subscribeItems()
            subscribeDragEvent()
            subscribeSwipeDeleteEvent()
        }

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }

        loadItems()
    }

    private fun createRecyclerViewConfig(): RecyclerViewConfig = RecyclerViewConfig().apply {
        itemTouchHelperSuppliers.addAll(touchHelpers)
    }

    private fun subscribeItems() {
        itemUpdateSubject.observeOn(schedulerFactory.ui())
            .doOnNext { listAdapter.submitList(it) }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeDragEvent() {
        dragCallback.eventSubject
            .observeOn(schedulerFactory.ui())
            .doOnNext { swapItems(it.fromPosition, it.toPosition) }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeSwipeDeleteEvent() {
        swipeCallback.eventSubject
            .observeOn(schedulerFactory.ui())
            .doOnNext { removeItems(it.position) }
            .subscribe()
            .addTo(disposables)
    }

    private fun loadItems() {
        pagingItemRepository.getItems(0, 300)
            .subscribeOn(schedulerFactory.io())
            .flatMapPublisher { Flowable.fromIterable(it) }
            .map { itemModelFactory.create(it) }
            .toList()
            .doOnSuccess {
                itemUpdateSubject.onNext(it)

                if (isShowErrorView.get()) {
                    isShowErrorView.set(false)
                }
            }
            .doOnError { isShowErrorView.set(true) }
            .subscribe()
            .addTo(disposables)
    }

    private fun swapItems(fromPosition: Int, toPosition: Int) = itemUpdateSubject.value?.run {
        val currentSize = size

        val isValidSize = fromPosition < currentSize && toPosition < currentSize
        if (isValidSize) {
            ArrayList(this)
                .apply { Collections.swap(this, fromPosition, toPosition) }
                .run { itemUpdateSubject.onNext(this) }
        }
    }

    private fun removeItems(targetIndex: Int) = itemUpdateSubject.value?.run {
        val isValidSize = targetIndex < size
        if (isValidSize) {
            ArrayList(this)
                .apply { removeAt(targetIndex) }
                .run { itemUpdateSubject.onNext(this) }
        }
    }

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    override fun refresh() {
        loadItems()
    }

    fun onBackPressed() = activityCommonUsecase.onBackPressed()
}