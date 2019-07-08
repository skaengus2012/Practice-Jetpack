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
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Home 상단 프로필에 대한 이벤트 정의
 *
 * @author Doohyun
 */
class HomeHeaderViewModel @Inject constructor(
        resourceProvider: ResourceProvider,
        private val schedulerFactory: SchedulerFactory
) : BindingItemViewModel() {

    private var timerDisposable: Disposable? = null

    private val dateFormat: CharSequence = resourceProvider.getString(R.string.home_time_format)

    @Bindable
    var currentTimeString: String = getCurrentTimeDateFormat()
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentTimeString)
        }

    override fun getLayoutRes(): Int = R.layout.view_home_header

    fun startTimer() {
        timerDisposable = Observable.timer(100, TimeUnit.MILLISECONDS)
                .repeat()
                .map { getCurrentTimeDateFormat() }
                .observeOn(schedulerFactory.ui())
                .filter { it != currentTimeString }
                .doOnNext { currentTimeString = it }
                .subscribe()
    }

    fun stopTimer() {
        timerDisposable?.dispose()
    }

    private fun getCurrentTimeDateFormat(): String = TimeBuilder.Create()
            .getStringFormat(dateFormat.toString())
            .blockingGet("")
}