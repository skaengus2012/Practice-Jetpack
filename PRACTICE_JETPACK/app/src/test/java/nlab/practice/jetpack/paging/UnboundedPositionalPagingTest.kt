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

package nlab.practice.jetpack.paging

import androidx.paging.PositionalDataSource.*
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Unbounded Positional 페이징을 동작하는 모듈에 대한 테스트
 *
 * @author Doohyun
 */
class UnboundedPositionalPagingTest {
    private val disposables = CompositeDisposable()
    private val repository = TestRepository()
    private val initParam = LoadInitialParams(0, 20, 10, false)
    private val rangeParam = LoadRangeParams(2, 20)

    private lateinit var initCallback: LoadInitialCallback<NonePageableItem>
    private lateinit var rangeCallback: LoadRangeCallback<NonePageableItem>

    private lateinit var pagingManager: UnboundedPositionalPagingManager<NonePageableItem>

    @Suppress("UNCHECKED_CAST")
    @Before
    fun setup() {
        initCallback = mock(LoadInitialCallback::class.java) as LoadInitialCallback<NonePageableItem>
        rangeCallback = mock(LoadRangeCallback::class.java) as LoadRangeCallback<NonePageableItem>

        pagingManager = UnboundedPositionalPagingManager
                .Factory(mock(SchedulerFactory::class.java))
                .create(repository, disposables)
    }

    @Test
    fun testInitLoadCallbackSuccess() {
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject.subscribe { observeCodes += it.state }

        dataSource.loadInitial(initParam, initCallback)

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH), observeCodes)
    }

    @Test
    fun testLoadRangeCallbackSuccess() {
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject.subscribe{ observeCodes += it.state }

        dataSource.loadInitial(initParam, initCallback)
        dataSource.loadRange(rangeParam, rangeCallback)

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH,
                PositionalDataLoadState.LOAD_START,
                PositionalDataLoadState.LOAD_FINISH), observeCodes)
    }
}