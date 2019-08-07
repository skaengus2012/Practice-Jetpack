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

import androidx.paging.PositionalDataSource.*
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

import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.INIT_LOAD_FINISH
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.INIT_LOAD_START
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.INIT_LOAD_DATA_SIZE_CHANGED
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.INIT_LOAD_ERROR
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.INIT_LOAD_FINISH_NO_DATA
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.LOAD_START
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.LOAD_DATA_SIZE_CHANGED
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.LOAD_ERROR
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState.Companion.LOAD_FINISH

/**
 * Test for CountableDataSourceFactory
 *
 * 1. 초기 조회 사용 케이스
 * 1-1. 초기 조회 시, 비어있을 경우
 * 1-2. 초기 조회 시, totalCount 조회에서 에러
 * 1-3. 초기 조회 시, load 조회에서 에러
 * 1-4. 초기 조회 시, 정상적인 조회 체크
 * 1-5. 초기 조회 시, 정상적인 조회를 하나 데이터 사이즈가 변경된 경우
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class CountableDataSourceFactoryTest {

    @Mock
    lateinit var repository: Repository<SimpleVO>

    @Mock
    lateinit var initCallback: LoadInitialCallback<SimpleVO>

    @Mock
    lateinit var rangeCallback: LoadRangeCallback<SimpleVO>

    @Captor
    lateinit var initLoadResultListCaptor: ArgumentCaptor<List<SimpleVO>>

    @Captor
    lateinit var initLoadResultTotalCountCaptor: ArgumentCaptor<Int>

    @Captor
    lateinit var loadRangeResultListCaptor: ArgumentCaptor<List<SimpleVO>>

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
            awaitTerminalEvent()
            assertValue(listOf(INIT_LOAD_START, INIT_LOAD_FINISH_NO_DATA))
        }

        verify(initCallback, times(1)).onResult(
            initLoadResultListCaptor.capture(),
            anyInt(),
            initLoadResultTotalCountCaptor.capture())

        assertTrue(initLoadResultListCaptor.value.isEmpty())
        assertEquals(0, initLoadResultTotalCountCaptor.value)
    }

    private fun createInitParam(
        startPosition: Int,
        pageSize: Int,
        requestLoadSize: Int = pageSize * 2
    ) = LoadInitialParams(
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
            assertValue(listOf(INIT_LOAD_START, INIT_LOAD_ERROR))
        }

        verify(initCallback, never()).onResult(anyList(), anyInt(), anyInt())
        verify(initCallback, never()).onResult(anyList(), anyInt())
    }

    @Test(timeout = 1000)
    fun initLoad_PostFinishCodeAndInvokeCallback_IfBestCase() {
        initLoadTemplate(0, 100, 200, 1000)

        verify(initCallback, times(1)).onResult(
            initLoadResultListCaptor.capture(),
            anyInt(),
            initLoadResultTotalCountCaptor.capture())

        assertEquals(200, initLoadResultListCaptor.value.size)
        assertEquals(1000, initLoadResultTotalCountCaptor.value)
    }

    @Test(timeout = 1000)
    fun initLoad_PostDataSizeChangedCodeAndNeverInvokeCallback_IfTotalCountIsChanged() {
        initLoadTemplate(
            0,
            100,
            200,
            1000,
            1002,
            listOf(INIT_LOAD_START, INIT_LOAD_DATA_SIZE_CHANGED)
        )

        verify(initCallback, never()).onResult(anyList(), anyInt(), anyInt())
    }

    private fun initLoadTemplate(
        startPosition: Int,
        pageSize: Int,
        requestLoadSize: Int,
        totalCount: Int,
        loadTotalCount: Int = totalCount,
        expectedStates: List<String> = listOf(INIT_LOAD_START, INIT_LOAD_FINISH)
    ) {
        val initParam = createInitParam(startPosition, pageSize, requestLoadSize)
        val firstStartPosition = computeInitialLoadPosition(initParam, totalCount)
        val firstLoadingSize = computeInitialLoadSize(initParam, firstStartPosition, totalCount)

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

    private fun createItems(size: Int): List<SimpleVO> = (0 until size).map { SimpleVO() }

    @Test(timeout = 1000)
    fun loadRange_PostFinishCodeAndInvokeCallback_IfBestCase() {
        val startPosition = 400
        val loadSize = 100
        val totalCount = 1000

        `when`(repository.loadSingle(startPosition, loadSize))
            .thenReturn(Single.just(PositionalResponse(createItems(loadSize), totalCount)))

        initLoadTemplate(0, 100, 200, totalCount)
        loadRangeTemplate(startPosition, loadSize, listOf(LOAD_START, LOAD_FINISH))

        verify(rangeCallback, times(1)).onResult(loadRangeResultListCaptor.capture())
        assertEquals(100, loadRangeResultListCaptor.value.size)
    }

    private fun loadRangeTemplate(
        startPosition: Int,
        loadSize: Int,
        expectedStates: List<String>
    ) {
        val rangeParam = LoadRangeParams(startPosition, loadSize)

        val testObserver = TestObserver<List<String>>()

        with(dataSourceFactory) {
            loadEventObservable
                .buffer(2)
                .firstElement()
                .flatMapObservable { Observable.fromIterable(it) }
                .map { it.state }
                .toList()
                .subscribe(testObserver)

            loadRange(rangeParam, rangeCallback)
        }

        with(testObserver) {
            awaitTerminalEvent()
            assertValue(expectedStates)
        }
    }

    @Test(timeout = 1000)
    fun loadRange_PostDataSizeChangedCodeAndNeverInvokeCallback_IfTotalCountIsChanged() {
        val startPosition = 400
        val loadSize = 100
        val totalCount = 1000

        `when`(repository.loadSingle(startPosition, loadSize))
            .thenReturn(Single.just(PositionalResponse(createItems(loadSize), 1002)))

        initLoadTemplate(0, 100, 200, totalCount)
        loadRangeTemplate(startPosition, loadSize, listOf(LOAD_START, LOAD_DATA_SIZE_CHANGED))

        verify(rangeCallback, never()).onResult(anyList())
    }

    @Test(timeout = 1000)
    fun loadRange_PostErrorCodeAndNeverInvokeCallback_IfLoadSingleIsError() {
        val startPosition = 400
        val loadSize = 100
        val totalCount = 1000

        `when`(repository.loadSingle(startPosition, loadSize)).thenReturn(Single.error(Throwable()))

        initLoadTemplate(0, 100, 200, totalCount)
        loadRangeTemplate(startPosition, loadSize, listOf(LOAD_START, LOAD_ERROR))

        verify(rangeCallback, never()).onResult(anyList())
    }
}