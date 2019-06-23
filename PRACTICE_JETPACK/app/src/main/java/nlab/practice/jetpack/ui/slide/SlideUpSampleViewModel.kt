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
        private val disposable: CompositeDisposable,
        private val listAdapterExampleFactory: ListAdapterExampleItemViewModelFactory,
        private val pagingRepository: PagingItemRepository,
        private val schedulerFactory: SchedulerFactory,
        private val playerRepository: PlayerRepository,
        private val playerController: PlayController,
        private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase,
        layoutManagerFactory: LayoutManagerFactory,
        itemDecoration: ListAdapterExampleItemDecoration,
        lifeCycleBinder: ActivityLifeCycleBinder,
        activityCallback: ActivityCallback,
        activityCommonUsecase: ActivityCommonUsecase): ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
        itemDecorations.add(itemDecoration)
    }

    private val isShowErrorView = ObservableBoolean(false)

    private val listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.createDefault(ArrayList())

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            refresh()
            initializePanel()

            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        activityCallback.onBackPressed { when {
            slidingUpPanelLayoutUsecase.currentPanelState.isExpanded() -> {
                slidingUpPanelLayoutUsecase.collapse()
                true
            }

            else -> false
        }}

        subscribeItemChanged()
    }

    private fun initializePanel() {
        slidingUpPanelLayoutUsecase.initialize()

        Single.fromCallable { playerRepository.getRandomTrack() }
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.ui())
                .doOnSuccess {
                    playerController.trackChangeSubject.onNext(it)

                    if (slidingUpPanelLayoutUsecase.currentPanelState.isHidden()) {
                        slidingUpPanelLayoutUsecase.collapse()
                    }
                }
                .subscribe()
                .addTo(disposable)
    }

    private fun subscribeItemChanged() {
        listUpdateSubject
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(disposable)
    }

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    override fun refresh() {
        pagingRepository.getItems(0, 100)
                .subscribeOn(schedulerFactory.io())
                .observeOn(schedulerFactory.ui())
                .doOnSuccess {
                    isShowErrorView.set(false)

                    it.map(listAdapterExampleFactory::create).run { listUpdateSubject.onNext(this) }
                }
                .doOnError {
                    isShowErrorView.set(true)
                }
                .subscribe()
                .addTo(disposable)
    }
}