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

import androidx.paging.PositionalDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import nlab.practice.jetpack.ignoreRxDoOnError
import nlab.practice.jetpack.testSchedulerFactoryOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import org.junit.Before

/**
 * Test for CountableDataSourceProvider.initLoad
 *
 * 1. 초기 조회 시, 비어있을 경우
 * 2. 초기 조회 시, totalCount 조회에서 에러
 * 3. 초기 조회 시, load 조회에서 에러
 * 4. 초기 조회 시, 정상적인 조회 체크
 * 5. 초기 조회 시, 정상적인 조회를 하나 데이터 사이즈가 변경된 경우
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class CountableDataSourceFactoryInitLoadTest {

    @Mock
    lateinit var repository: Repository<SimpleVO>

    @Mock
    lateinit var initCallback: PositionalDataSource.LoadInitialCallback<SimpleVO>

    @Captor
    lateinit var resultListCaptor: ArgumentCaptor<List<SimpleVO>>

    @Captor
    lateinit var resultTotalCountCaptor: ArgumentCaptor<Int>

    private lateinit var dataSourceFactory: CountableDataSourceFactory<SimpleVO>

    @Before
    fun initialize() {
        dataSourceFactory = CountableDataSourceFactory(repository, testSchedulerFactoryOf())
        ignoreRxDoOnError()
    }

    @Test(timeout = 1000)
    fun initLoad_PostNoDataCodeAndInvokeCallback_IfTotalCountIsZero() {
        `when`(repository.totalCountSingle()).thenReturn(Single.just(0))

        initLoadEmptyTemplate()
    }

    private fun initLoadEmptyTemplate() {
        val testObserver = TestObserver<List<String>>()

        with(dataSourceFactory) {
            loadEventObservable
                .buffer(2)
                .firstElement()
                .flatMapObservable { Observable.fromIterable(it) }
                .map { it.state }
                .toList()
                .subscribe(testObserver)

            loadInitial(createInitParam(0, 10), initCallback)
        }

        with(testObserver) {
            assertValue(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH_NO_DATA
            ))
        }

        verify(initCallback, times(1)).onResult(
            resultListCaptor.capture(),
            anyInt(),
            resultTotalCountCaptor.capture())

        assertTrue(resultListCaptor.value.isEmpty())
        assertEquals(0, resultTotalCountCaptor.value)
    }

    private fun createInitParam(
        startPosition: Int,
        pageSize: Int,
        requestLoadSize: Int = pageSize * 2
    ) = PositionalDataSource.LoadInitialParams(
        startPosition,
        requestLoadSize,
        pageSize,
        true
    )

    @Test(timeout = 1000)
    fun initLoad_PostErrorCodeAndNeverInvokeCallback_IfTotalCountSingleIsError() {
        `when`(repository.totalCountSingle()).thenReturn(Single.error(Throwable()))

        initLoadErrorTemplate()
    }

    @Test(timeout = 1000)
    fun initLoad_PostErrorCodeAndNeverInvokeCallback_IfLoadSingleIsError() {
        `when`(repository.totalCountSingle()).thenReturn(Single.just(1))
        `when`(repository.loadSingle(anyInt(), anyInt())).thenReturn(Single.error(Throwable()))

        initLoadErrorTemplate()
    }

    private fun initLoadErrorTemplate() {
        val testObserver = TestObserver<List<String>>()

        with(dataSourceFactory) {
            loadEventObservable
                .buffer(2)
                .firstElement()
                .flatMapObservable { Observable.fromIterable(it) }
                .map { it.state }
                .toList()
                .subscribe(testObserver)

            loadInitial(createInitParam(0, 10), initCallback)
        }

        with(testObserver) {
            awaitTerminalEvent()
            assertValue(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_ERROR
            ))
        }

        verify(initCallback, never()).onResult(anyList(), anyInt(), anyInt())
        verify(initCallback, never()).onResult(anyList(), anyInt())
    }

    @Test(timeout = 1000)
    fun initLoad_PostFinishCodeAndInvokeCallback_IfBestCase() {
        initLoadTemplate(0, 100, 200, 1000)

        verify(initCallback, times(1)).onResult(
            resultListCaptor.capture(),
            anyInt(),
            resultTotalCountCaptor.capture())

        assertEquals(200, resultListCaptor.value.size)
        assertEquals(1000, resultTotalCountCaptor.value)
    }

    @Test(timeout = 1000)
    fun initLoad_PostDataSizeChangedCodeAndNeverInvokeCallback_IfTotalCountIsChanged() {
        initLoadTemplate(0, 100, 200, 1000, 1002,
            listOf(PositionalDataLoadState.INIT_LOAD_START, PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED))

        verify(initCallback, never()).onResult(anyList(), anyInt(), anyInt())
    }

    private fun createItems(size: Int): List<SimpleVO> = (0 until size).map { SimpleVO() }

    private fun initLoadTemplate(
        startPosition: Int,
        pageSize: Int,
        requestLoadSize: Int,
        totalCount: Int,
        loadTotalCount: Int = totalCount,
        expectedStates: List<String> = listOf(
            PositionalDataLoadState.INIT_LOAD_START, PositionalDataLoadState.INIT_LOAD_FINISH)
    ) {
        val initParam = createInitParam(startPosition, pageSize, requestLoadSize)
        val firstStartPosition = PositionalDataSource.computeInitialLoadPosition(initParam, totalCount)
        val firstLoadingSize = PositionalDataSource.computeInitialLoadSize(initParam, firstStartPosition, totalCount)

        `when`(repository.totalCountSingle()).thenReturn(Single.just(totalCount))
        `when`(repository.loadSingle(firstStartPosition, firstLoadingSize))
            .thenReturn(Single.just(PositionalResponse(createItems(requestLoadSize), loadTotalCount)))


        val testObserver = TestObserver<List<String>>()

        with(dataSourceFactory) {
            loadEventObservable
                .buffer(2)
                .firstElement()
                .flatMapObservable { Observable.fromIterable(it) }
                .map { it.state }
                .toList()
                .subscribe(testObserver)

            loadInitial(initParam, initCallback)
        }

        with(testObserver) {
            awaitTerminalEvent()
            assertValue(expectedStates)
        }
    }
}