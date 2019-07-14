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

package nlab.practice.jetpack.ui.slide

import androidx.databinding.ObservableBoolean
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.repository.PlayerRepository
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemDecoration
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemViewModel
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemViewModelFactory
import nlab.practice.jetpack.util.PlayController
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.ActivityCallback
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.isExpanded
import nlab.practice.jetpack.util.slidingpanel.isHidden
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleViewModel @Inject constructor(
    private val listAdapterExampleFactory: ListAdapterExampleItemViewModelFactory,
    private val pagingRepository: PagingItemRepository,
    private val schedulerFactory: SchedulerFactory,
    private val playerRepository: PlayerRepository,
    private val playerController: PlayController,
    private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase,
    private val slidingUpSampleBundle: SlideUpSampleBundle,
    layoutManagerFactory: LayoutManagerFactory,
    itemDecoration: ListAdapterExampleItemDecoration,
    lifeCycleBinder: ActivityLifeCycleBinder,
    activityCallback: ActivityCallback,
    activityCommonUsecase: ActivityCommonUsecase
) : ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
        itemDecorations += itemDecoration
    }

    private val disposables = CompositeDisposable()

    private val isShowErrorView = ObservableBoolean(false)

    private val listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> =
        BehaviorSubject.createDefault(ArrayList())

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            doOnCreate()

            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            slidingUpSampleBundle.clear()

            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_DESTROY) {
            collapsingPanelIfHidden()

            disposables.clear()
        }

        activityCallback.onBackPressed {
            when {
                slidingUpPanelLayoutUsecase.currentPanelState.isExpanded() -> {
                    slidingUpPanelLayoutUsecase.collapsed()
                    true
                }

                else -> false
            }
        }
    }

    private fun doOnCreate() {
        subscribeItemChanged()

        schedulerFactory.ui()
            .scheduleDirect {
                // 패널의 상태가 내부에서 뷰가 재생성될 때 다음 제대로 등장하지 않음
                // 또한, Panel 에 대한 명령이 바로 처리가 되지 않기 때문에 POST 처리가 필요.
                initializePanel()
                initializeTrack()
                initializePagingItem()
            }
            .addTo(disposables)
    }

    private fun subscribeItemChanged() {
        listUpdateSubject
            .doOnNext { listAdapter.submitList(it) }
            .subscribe()
            .addTo(disposables)
    }

    private fun initializePanel() {
        slidingUpPanelLayoutUsecase.initialize()

        slidingUpSampleBundle.panelState?.let { slidingUpPanelLayoutUsecase.currentPanelState = it }

        slidingUpPanelLayoutUsecase.slidePanelStateSubject
            .subscribe { slidingUpSampleBundle.panelState = it }
            .addTo(disposables)
    }

    private fun initializeTrack() {
        if (playerController.latestTrack == null) {
            slidingUpPanelLayoutUsecase.hidden()

            Single.fromCallable { playerRepository.getRandomTrack() }
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.ui())
                .doOnSuccess {
                    playerController.post(it)

                    collapsingPanelIfHidden()
                }
                .subscribe()
                .addTo(disposables)
        }
    }

    private fun collapsingPanelIfHidden() {
        slidingUpPanelLayoutUsecase.currentPanelState
            .takeIf { state -> state.isHidden() }
            ?.let { slidingUpPanelLayoutUsecase.collapsed() }
    }

    private fun initializePagingItem() {
        slidingUpSampleBundle.pagingItems
            ?.let { updateItem(it) }
            ?: run { refresh() }
    }

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    override fun refresh() {
        pagingRepository.getItems(0, 100)
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.ui())
            .doOnSuccess {
                isShowErrorView.set(false)
                updateItem(it)
            }
            .doOnError {
                isShowErrorView.set(true)
            }
            .subscribe()
            .addTo(disposables)
    }

    private fun updateItem(pagingItems: List<PagingItem>) {
        pagingItems
            .asSequence()
            .map(listAdapterExampleFactory::create)
            .toList()
            .let { listUpdateSubject.onNext(it) }

        slidingUpSampleBundle.pagingItems = pagingItems
    }
}