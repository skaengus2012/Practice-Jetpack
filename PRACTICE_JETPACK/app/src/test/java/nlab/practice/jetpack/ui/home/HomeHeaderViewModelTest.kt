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

package nlab.practice.jetpack.ui.home

import Njava.util.time.TimeBuilder
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.RxBaseObservables
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
import nlab.practice.jetpack.testSchedulerFactoryOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit


private const val MOCK_MESSAGE_HOME_TIME_FORMAT_STRING = "현재 시간 MM월 dd일 hh시 mm분"

/**
 * Test for HomeHeaderViewModel
 *
 * 1. 타이머의 텍스트 변경이 1분 단위로 변경되는가?
 * 2. stopTimer 가 요청된 후, 타이머가 멈췄는가?
 * 3. 생명주기에 따라 timer 가 제대로 시작되고 종료되었는가?
 *
 * @author Doohyun
 * @since 2019. 07. 30
 */
@RunWith(MockitoJUnitRunner::class)
class HomeHeaderViewModelTest {

    private val schedulerFactory = testSchedulerFactoryOf(
        computation = Schedulers.computation()
    )

    private val lifecycleBinder = FragmentLifecycleBinder()

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private lateinit var viewModel: HomeHeaderViewModel

    @Before
    fun initialize() {
        `when`(resourceProvider.getString(R.string.home_time_format)).thenReturn(MOCK_MESSAGE_HOME_TIME_FORMAT_STRING)

        viewModel = HomeHeaderViewModel(resourceProvider, lifecycleBinder, schedulerFactory)
    }

    @Test(timeout = 60000L)
    fun when_PassedMinute_expected_CurrentTimeStringWillBeChanged() {
        val currentTimeText = viewModel.currentTimeString

        val expectedValue = TimeBuilder.Create(currentTimeText, MOCK_MESSAGE_HOME_TIME_FORMAT_STRING)
            .addMinute(1)
            .getStringFormat(MOCK_MESSAGE_HOME_TIME_FORMAT_STRING)
            .blockingGet()

        val testObserver = TestObserver<String>()

        RxBaseObservables.of(viewModel)
            .toObservable()
            .filter { it.propertyId == BR.currentTimeString }
            .map { viewModel.currentTimeString }
            .filter { it != currentTimeText }
            .firstElement()
            .subscribe(testObserver)

        lifecycleBinder.apply(FragmentLifecycle.ON_VIEW_CREATED)

        with(testObserver) {
            awaitTerminalEvent()
            assertValue(expectedValue)
        }
    }

    @Test
    fun when_AfterInvokeStopTimer_CurrentTimeStringIsNeverChanged() {
        lifecycleBinder.apply(FragmentLifecycle.ON_VIEW_CREATED)
        lifecycleBinder.apply(FragmentLifecycle.ON_DESTROY_VIEW)

        val expectedValue = viewModel.currentTimeString

        val testObserver = TestObserver<String>()

        Completable.timer(1, TimeUnit.SECONDS)
            .observeOn(schedulerFactory.ui())
            .subscribe(testObserver)

        testObserver.awaitTerminalEvent()

        assertEquals(expectedValue, viewModel.currentTimeString)
    }


}