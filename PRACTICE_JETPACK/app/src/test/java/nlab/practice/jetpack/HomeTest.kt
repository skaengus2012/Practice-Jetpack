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

package nlab.practice.jetpack

import Njava.util.time.TimeBuilder
import io.reactivex.Single
import nlab.practice.jetpack.ui.home.HomeHeaderViewModel
import nlab.practice.jetpack.util.ResourceProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

import androidx.databinding.Observable.OnPropertyChangedCallback
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.util.SchedulerFactory

private const val HOME_TIME_FORMAT_STRING = "현재 시간 MM월 dd일 hh시 mm분"

/**
 * Home 화면에 대한 유닛테스트 정의
 *
 * @author Doohyun
 */
class HomeTest {

    private lateinit var resourceProvider: ResourceProvider

    private lateinit var schedulerFactory: SchedulerFactory

    @Before
    fun setup() {
        resourceProvider = mock(ResourceProvider::class.java).apply {
            `when`( this.getText(R.string.home_time_format)).thenReturn(HOME_TIME_FORMAT_STRING)
        }

        schedulerFactory = mock(SchedulerFactory::class.java).apply {
            `when`( this.ui() ).thenReturn(Schedulers.single())
        }
    }

    /**
     * Home Header Timer 처리에 대한 유닛테스트 정의
     */
    @Test
    fun testHomeHeaderTimer() {
        val homeHeaderViewModel = HomeHeaderViewModel(resourceProvider, schedulerFactory)

        val startTimeString = homeHeaderViewModel.currentTimeString
        homeHeaderViewModel.startTimer()

        val testObserver = TestObserver<Boolean>()

        Single.create<Boolean> {
                    emitter
                    ->
                    homeHeaderViewModel.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
                        override fun onPropertyChanged(sender: androidx.databinding.Observable?, propertyId: Int) {
                            homeHeaderViewModel.stopTimer()

                            // 1분씩 데이터 변경이 있어야함. 기존 추출한 데이터에서 1분을 더한 값과 일치되는지 확인
                            val addOneMinuteDate = TimeBuilder.Create(startTimeString, HOME_TIME_FORMAT_STRING)
                                    .addMinute(1)
                                    .getStringFormat(HOME_TIME_FORMAT_STRING)
                                    .blockingGet("")

                            emitter.onSuccess(addOneMinuteDate == homeHeaderViewModel.currentTimeString)
                        }
                    })
                }
                .doOnSuccess { assert(it) }
                .subscribe(testObserver)

        testObserver.run {
            awaitTerminalEvent()
            assertValue(true)
        }
    }
}