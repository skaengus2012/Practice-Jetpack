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

package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.reactivex.subscribers.TestSubscriber
import nlab.practice.jetpack.testSchedulerFactoryOf
import nlab.practice.jetpack.util.recyclerview.paging.PagedListItemPresenter
import nlab.practice.jetpack.util.recyclerview.paging.pagedListItemTransformerOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test for PositionalPagingUsecase
 *
 * 1. pagedList 추출이 가능해야함
 * 2. invalidate 상황 시, pagedList 추출이 새롭게 처리되어야함
 * 3. 실패입력이 들어올 시, retry 가 가능해야함
 * 4. 설정을 할 수 있어야함
 * 5. 데이터 복원이 가능해야함
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class PositionalPagingUsecaseTest {

    @Mock
    lateinit var dataSourceFactory: DataSourceFactory<SimpleVO>

    @Mock
    lateinit var loadCallback: PositionalDataSource.LoadRangeCallback<SimpleVO>

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(100)
        .setPageSize(100)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    private val schedulerFactory = testSchedulerFactoryOf()

    private fun createPagingUsecase(
        mocking: () -> Unit = {
            `when`(dataSourceFactory.createDataSource()).thenAnswer { InternalPositionalDataSource(dataSourceFactory) }
        }
    ): PagingUsecase<SimpleVO> {
        mocking.invoke()

        return PagingUsecase.Factory(
                schedulerFactory,
                dataSourceFactory
            )
            .create(pagedListItemTransformerOf { SimpleItem(it) })
    }

    @Test
    fun when_SubscribePagedListFlowableAtFirst_expected_ObserveValueCountIsOne() {
        createPagingUsecase().createPagedListFlowable(config)
            .test()
            .assertValueCount(1)
    }

    @Test(timeout = 1000)
    fun when_SubscribePagedListFlowableAndInvalidate_expected_ObserveValueCountIsTwo() {
        val testSubscriber = TestSubscriber<PagedList<PagedListItemPresenter<SimpleVO>>>()

        with(createPagingUsecase()) {
            createPagedListFlowable(config)
                .take(2)
                .subscribe(testSubscriber)

            invalidate()
        }

        with(testSubscriber) {
            awaitTerminalEvent()
            assertValueCount(2)
        }
    }

    @Test
    fun when_InvokeLoadRangeOfDataSourceAndRetryOfPagedList_expected_InvokeCountOfLoadRangeDataSourceFactoryIsTwo() {
        val dataSource = InternalPositionalDataSource(dataSourceFactory)

        val param = PositionalDataSource.LoadRangeParams(100, 100)
        dataSource.loadRange(param, loadCallback)

        with(createPagingUsecase {
            `when`(dataSourceFactory.createDataSource()).thenReturn(dataSource)
        }) {
            createPagedListFlowable(config).subscribe()
            retryLoadRange()
        }

        verify(dataSourceFactory, times(2)).loadRange(param, loadCallback)
    }
}