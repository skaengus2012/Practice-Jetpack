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
import nlab.practice.jetpack.testSchedulerFactoryOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test for CountableDataSourceFactory.loadRange
 *
 * 1. 일반 범위 조회 시, 정상적인 조회 체크
 * 2. 일반 범위 조회 시, 정상적인 조회를 하나 데이터 사이즈가 변경된 경우
 * 3. 일반 범위 조회 시, 에러가 발생한 경우
 *
 * @author Doohyun
 * @since 2019. 08. 07
 */
@RunWith(MockitoJUnitRunner::class)
class CountableDataSourceFactoryLoadRangeTest {

    @Mock
    lateinit var repository: Repository<SimpleVO>

    @Mock
    lateinit var rangeCallback: PositionalDataSource.LoadRangeCallback<SimpleVO>

    private lateinit var dataSourceFactory: CountableDataSourceFactory<SimpleVO>

    @Before
    fun initialize() {
        dataSourceFactory = CountableDataSourceFactory(repository, testSchedulerFactoryOf())
    }

    @Test(timeout = 1000)
    fun loadRange() {

    }


    @Test(timeout = 1000)
    fun loadRangeIfTotalCountChanged() {

    }

    @Test(timeout = 1000)
    fun loadRangeIfError() {

    }
}