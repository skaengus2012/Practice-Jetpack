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
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
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
    fragmentLifeCycleBinder: FragmentLifeCycleBinder,
    private val pagingItemRepository: PagingItemRepository,
    private val pagingItemViewModelFactory: PagingItemPracticeViewModelFactory,
    private val activityCommonUsecase: ActivityCommonUsecase,
    private val resourceProvider: ResourceProvider,
    private val toastHelper: ToastHelper,
    private val schedulerFactory: SchedulerFactory,
    private val imagePoolRepository: ImagePoolRepository,
    pagingManagerFactory: UnboundedPositionalPagingManager.Factory
) : PagingViewModel {

    private val subTitle = ObservableField(resourceProvider.getString(R.string.paging_unbounded_sub_title).toString())

    private val isShowRefreshProgressBar = ObservableBoolean(false)

    private val isShowErrorView = ObservableBoolean(false)

    private val disposables = CompositeDisposable()

    private val pagingManager = pagingManagerFactory.create(pagingItemRepository, disposables)

    private val bottomMoreViewModel: BottomMoreViewModel

    private lateinit var listAdapter: BindingPagedListAdapter<PagingItemPracticeViewModel>

    private val singleScheduler = schedulerFactory.single()

    init {
        bottomMoreViewModel = BottomMoreViewModel {
            listAdapter.isShowBottomProgress = false

            pagingManager.retry()
        }

        listAdapter = BindingPagedListAdapter(bottomMoreItem = bottomMoreViewModel)

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            subscribePagedList()
            subscribeLoadStart()
            subscribeLoadComplete()
            subscribeLoadError()
        }

        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }
    }

    private fun subscribePagedList() {
        val config = PagedList.Config.Builder()
            .setPageSize(100)
            .setPrefetchDistance(20)
            .setEnablePlaceholders(false)
            .build()

        RxPagedListBuilder<Int, PagingItemPracticeViewModel>(createDataSourceFactory(), config)
            .buildFlowable(BackpressureStrategy.BUFFER)
            .doOnNext { listAdapter.submitList(it) }
            .observeOn(schedulerFactory.ui())
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeLoadStart() {
        pagingManager.stateSubject
            .filter { it.state == PositionalDataLoadState.LOAD_START }
            .observeOn(schedulerFactory.ui())
            .doOnNext {
                bottomMoreViewModel.showProgress = true
                listAdapter.isShowBottomProgress = true
            }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeLoadComplete() {
        pagingManager.stateSubject
            .filter { it.state == PositionalDataLoadState.LOAD_FINISH }
            .observeOn(schedulerFactory.ui())
            .doOnNext { listAdapter.isShowBottomProgress = false }
            .subscribe()
            .addTo(disposables)

        pagingManager.stateSubject
            .filter { it.state == PositionalDataLoadState.INIT_LOAD_FINISH }
            .observeOn(schedulerFactory.ui())
            .doOnNext { isShowErrorView.set(false) }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeLoadError() {
        pagingManager.stateSubject
            .filter { it.state == PositionalDataLoadState.LOAD_ERROR }
            .observeOn(schedulerFactory.ui())
            .doOnNext { bottomMoreViewModel.showProgress = false }
            .subscribe()
            .addTo(disposables)

        pagingManager.stateSubject
            .filter { it.state == PositionalDataLoadState.INIT_LOAD_ERROR }
            .observeOn(schedulerFactory.ui())
            .doOnNext { isShowErrorView.set(true) }
            .subscribe()
            .addTo(disposables)
    }

    override fun getListAdapter(): BindingPagedListAdapter<PagingItemPracticeViewModel> = listAdapter

    override fun getSubTitle(): ObservableField<String> = subTitle

    override fun isShowRefreshProgressBar(): ObservableBoolean = isShowRefreshProgressBar

    override fun getBannerText(): String = resourceProvider.getString(R.string.paging_banner_to_countable).toString()

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    override fun refresh() {
        isShowRefreshProgressBar.set(true)

        // NOTE
        // invalidate 호출 시, 잘못된 위치로 requestPosition 이 세팅되는 이슈 존재
        // 리프레쉬로 업데이트 시, 현재 pagedList 에 0을 지정해주도록 하자.
        listAdapter.currentList
            ?.takeIf { !it.isEmpty() }
            ?.run { loadAround(0) }

        toastHelper.showToast(R.string.paging_notify_refresh)
        pagingManager.invalidate()
    }

    override fun addItems() {
        Single.fromCallable { addItemsInternal() }
            .subscribeOn(singleScheduler)
            .observeOn(schedulerFactory.ui())
            .doOnSuccess {
                resourceProvider.getString(R.string.paging_add_item_unbounded_format, it.first, it.second).run {
                    toastHelper.showToast(this)
                }
            }
            .subscribe()
            .addTo(disposables)
    }

    /**
     * Total 을 알 수 없다는 가정하에, 랜덤으로 데이터를 추가하는 로직 정의
     *
     * @return [시작하는 index, 추가된 아이템 개수] 가 출력
     */
    private fun addItemsInternal(): Pair<Int, Int> {
        val newItemCount = Random.nextInt(2, 50)
        val randomStartIndex = Random.nextInt(1500)

        val imageTotalSize = imagePoolRepository.getSize()
        var startChoiceImage = Random.nextInt(imageTotalSize)
        var startIndex = randomStartIndex

        (0..newItemCount).map {
            val index = startIndex++
            val imageIndex = startChoiceImage++
            PagingItem(index, "New Instant Item - index : $index", imagePoolRepository.get(imageIndex % imageTotalSize))
        }.run { pagingItemRepository.addItems(this) }

        return Pair(randomStartIndex, newItemCount)
    }

    override fun onClickBackButton() {
        activityCommonUsecase.onBackPressed()
    }

    private fun createDataSourceFactory(): DFactory = object : DFactory() {

        override fun create(): DataSource<Int, PagingItemPracticeViewModel> {
            isShowRefreshProgressBar.set(false)
            return pagingManager.newDataSource().map { createPagingItemViewModel(it) }
        }

        fun createPagingItemViewModel(pagingItem: PagingItem): PagingItemPracticeViewModel =
            pagingItemViewModelFactory.create(pagingItem) {
                it.navCountablePaging()
            }
    }

}