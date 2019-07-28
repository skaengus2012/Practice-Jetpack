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
import nlab.practice.jetpack.util.di.activity.ActivityCommonUsecase
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author Doohyun
 */
class CountablePagingViewModel @Inject constructor(
    lifeCycleBinder: FragmentLifeCycleBinder,
    pagingManagerFactory: CountablePositionalPagingManager.Factory,
    private val activityCommonUsecase: ActivityCommonUsecase,
    private val pagingItemRepository: PagingItemRepository,
    private val imagePoolRepository: ImagePoolRepository,
    private val schedulerFactory: SchedulerFactory,
    private val toastHelper: ToastHelper,
    private val resourceProvider: ResourceProvider,
    private val recyclerViewUsecase: RecyclerViewUsecase,
    private val pagingItemViewModelFactory: PagingItemPracticeViewModelFactory
) : PagingViewModel {

    private val disposables = CompositeDisposable()

    private val listAdapter = BindingPagedListAdapter<PagingItemPracticeViewModel>(
        placeholderResId = R.layout.view_paging_item_placeholder
    )

    private val isShowErrorView = ObservableBoolean(false)
    private val isShowRefreshProgressBar = ObservableBoolean(false)
    private var isRefreshing = false

    private val subTitle = ObservableField<String>()
    private val subTitleFormat = resourceProvider.getText(R.string.paging_countable_sub_title_format)

    private val pagingManager = pagingManagerFactory.create(pagingItemRepository, disposables)
    private val singleScheduler = schedulerFactory.single()

    init {
        loadTitle()

        lifeCycleBinder.bindUntil(FragmentLifecycle.ON_VIEW_CREATED) {
            subscribePagedList()
            subscribeTotalCountChanged()
            subscribeLoadFinish()
            subscribeLoadError()
        }

        lifeCycleBinder.bindUntil(FragmentLifecycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }
    }

    override fun isShowRefreshProgressBar(): ObservableBoolean = isShowRefreshProgressBar

    override fun getListAdapter(): BindingPagedListAdapter<PagingItemPracticeViewModel> = listAdapter

    override fun getSubTitle(): ObservableField<String> = subTitle

    override fun getBannerText(): String = resourceProvider.getText(R.string.paging_banner_to_unbounded).toString()

    override fun onClickBackButton() = activityCommonUsecase.onBackPressed()

    override fun isShowErrorView(): ObservableBoolean = isShowErrorView

    private fun subscribePagedList() {
        // NOTE : placeHolder 는 아이템의 전체 개수를 알고 있을 때만 사용 가능
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(100)
            .setPageSize(100)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()

        RxPagedListBuilder(createDataSourceFactory(), config)
            .buildFlowable(BackpressureStrategy.BUFFER)
            .doOnNext { listAdapter.submitList(it) }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeTotalCountChanged() {
        pagingManager.stateSubject
            .filter {
                it.state in listOf(
                    PositionalDataLoadState.LOAD_DATA_SIZE_CHANGED,
                    PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED
                )
            }
            .observeOn(schedulerFactory.ui())
            .doOnNext {
                loadTitle()
                toastHelper.showToast(R.string.paging_notify_data_size_changed)
                pagingManager.invalidate()
            }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeLoadError() {
        pagingManager.stateSubject
            .observeOn(schedulerFactory.ui())
            .filter { it.state == PositionalDataLoadState.LOAD_ERROR }
            .doOnNext {
                isRefreshing = false
                pagingManager.retry()
            }
            .subscribe()
            .addTo(disposables)

        pagingManager.stateSubject
            .observeOn(schedulerFactory.ui())
            .filter { it.state == PositionalDataLoadState.INIT_LOAD_ERROR }
            .doOnNext {
                isRefreshing = false
                isShowErrorView.set(true)
            }
            .subscribe()
            .addTo(disposables)
    }

    private fun subscribeLoadFinish() {
        pagingManager.stateSubject
            .observeOn(schedulerFactory.ui())
            .filter { it.state == PositionalDataLoadState.LOAD_FINISH }
            .filter { isRefreshing }
            .doOnNext {
                // invalidate 호출 시, init, range 두번 호출 해서, 리프레쉬로 업데이트 시 강제로 200번째 칸으로 스크롤 되는 이슈가 존재
                // 리프레쉬로 업데이트 시, 포지션을 0으로 지정해 주도록 하자.
                isRefreshing = false
                recyclerViewUsecase.scrollToPositionWithOffset(0, 0)
            }
            .subscribe()
            .addTo(disposables)

        pagingManager.stateSubject
            .observeOn(schedulerFactory.ui())
            .filter { it.state == PositionalDataLoadState.INIT_LOAD_FINISH }
            .doOnNext { isShowErrorView.set(false) }
            .subscribe()
            .addTo(disposables)
    }

    private fun loadTitle() {
        pagingItemRepository.getTotalCount()
            .subscribeOn(schedulerFactory.io())
            .observeOn(schedulerFactory.ui())
            .doOnSuccess { String.format(subTitleFormat.toString(), it).run { subTitle.set(this) } }
            .subscribe()
            .addTo(disposables)
    }

    override fun addItems() {
        pagingItemRepository.getTotalCount()
            .flatMap { getItemsAddingItemCountSingle(it) }
            .subscribeOn(singleScheduler)
            .observeOn(schedulerFactory.ui())
            .map { resourceProvider.getString(R.string.paging_add_item, it) }
            .doOnSuccess { toastHelper.showToast(it) }
            .doOnSuccess { loadTitle() }
            .subscribe()
            .addTo(disposables)
    }

    override fun refresh() {
        isShowRefreshProgressBar.set(true)

        isRefreshing = true
        toastHelper.showToast(R.string.paging_notify_refresh)
        pagingManager.invalidate()
    }

    private fun getItemsAddingItemCountSingle(totalSize: Int): Single<Int> = Single.fromCallable {
        val newItemCount = Random.nextInt(2, 30)

        val imageTotalSize = imagePoolRepository.getSize()
        var startChoiceImage = Random.nextInt(imageTotalSize)
        var startIndex = totalSize

        (0..newItemCount).map {
            val index = startIndex++
            val imageIndex = startChoiceImage++
            PagingItem(index, "New Instant Item - index : $index", imagePoolRepository.get(imageIndex % imageTotalSize))
        }.run { pagingItemRepository.addItems(this) }

        newItemCount
    }

    private fun createDataSourceFactory(): DFactory = object : DFactory() {

        override fun create(): DataSource<Int, PagingItemPracticeViewModel> {
            isShowRefreshProgressBar.set(false)
            return pagingManager.newDataSource().map { createPagingItemViewModel(it) }
        }

        fun createPagingItemViewModel(pagingItem: PagingItem): PagingItemPracticeViewModel =
            pagingItemViewModelFactory.create(pagingItem) {
                it.navUnboundedPaging()
            }
    }

}