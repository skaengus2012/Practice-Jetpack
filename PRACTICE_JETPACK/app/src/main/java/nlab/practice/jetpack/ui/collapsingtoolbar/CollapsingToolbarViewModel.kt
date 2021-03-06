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

package nlab.practice.jetpack.ui.collapsingtoolbar

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.CoverRepository
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.di.activity.ActivityCommonUsecase
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CollapsingToolbarViewModel @Inject constructor(
    lifeCycleBinder: ActivityLifecycleBinder,
    private val activityUsecase: ActivityCommonUsecase,
    private val toolbarUsecase: ToolbarItemVisibilityUsecase,
    private val schedulerFactory: SchedulerFactory,
    private val coverRepository: CoverRepository,
    private val pagingItemRepository: PagingItemRepository,
    private val resourceProvider: ResourceProvider,
    private val toastHelper: ToastHelper,
    private val snackBarHelper: SnackBarHelper,
    private val itemViewModelFactory: CollapsingPagingItemViewModelFactory
) : ListErrorPageViewModel {

    val coverImage: ObservableField<String> = ObservableField()

    val coverText = ObservableField<String>()

    val loadFinished = ObservableBoolean()

    val isShowCollapsingScrim = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<BindingItemViewModel>()

    private val disposables = CompositeDisposable()

    private val listUpdateSubject = BehaviorSubject.create<List<BindingItemViewModel>>()

    private val showErrorState = ObservableBoolean()

    init {
        refresh()

        lifeCycleBinder.bindUntil(ActivityLifecycle.ON_CREATE) {
            observeEvent()
            toolbarUsecase.initialize(false)
        }

        lifeCycleBinder.bindUntil(ActivityLifecycle.ON_DESTROY) {
            disposables.clear()
        }
    }

    private fun observeEvent() {
        listUpdateSubject
            .subscribe { listAdapter.submitList(it) }
            .addTo(disposables)

        toolbarUsecase.scrimVisibilityChangeSubject
            .subscribe { isShowCollapsingScrim.set(it) }
            .addTo(disposables)
    }

    private fun loadCover() = coverRepository.getCover()
        .subscribeOn(schedulerFactory.io())
        .observeOn(schedulerFactory.ui())
        .map {
            {
                coverImage.set(it.imageUrl)
                coverText.set(it.title)
            }
        }

    private fun loadItems() = pagingItemRepository.getItems(0, 100)
        .subscribeOn(schedulerFactory.io())
        .observeOn(schedulerFactory.ui())
        .map {
            it.withIndex().map { (index, item)
                ->
                itemViewModelFactory.create(item) {
                    val messageRes = resourceProvider.getText(R.string.collapsing_toolbar_ex_item_click)
                    toastHelper.showToast(String.format(messageRes.toString(), index))
                }
            }
        }
        .map { { listUpdateSubject.onNext(it) } }

    override fun isShowErrorView(): ObservableBoolean = showErrorState

    override fun refresh() {
        loadFinished.set(false)

        Single.merge(loadCover(), loadItems())
            .toList()
            .observeOn(schedulerFactory.ui())
            .doOnSuccess { executors
                ->
                executors.forEach { it() }
            }
            .doOnSuccess {
                showErrorState.set(false)
                loadFinished.set(true)
            }
            .doOnError { showErrorState.set(true) }
            .subscribe()
            .addTo(disposables)
    }

    fun onBackPress() = activityUsecase.onBackPressed()

    fun onFabClickEvent() {
        snackBarHelper.showSnackBar(R.string.collapsing_toolbar_fab_message, duration = Snackbar.LENGTH_SHORT)
    }
}