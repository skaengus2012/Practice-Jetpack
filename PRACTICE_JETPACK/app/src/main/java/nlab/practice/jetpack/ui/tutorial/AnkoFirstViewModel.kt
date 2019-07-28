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

package nlab.practice.jetpack.ui.tutorial

import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.RxBaseObservables
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstViewModel @Inject constructor(
    lifeCycleBinder: ActivityLifecycleBinder,
    private val schedulerFactory: SchedulerFactory,
    private val resourceProvider: ResourceProvider,
    private val lifecycleState: AnkoFirstState
) {

    private val disposables = CompositeDisposable()

    val message: ObservableField<String> = ObservableField()

    init {
        message.set(lifecycleState.message ?: resourceProvider.getString(R.string.anko_first_message))

        RxBaseObservables.of(message)
            .toObservable()
            .subscribe { lifecycleState.message = message.get() }
            .addTo(disposables)

        lifeCycleBinder.bindUntil(ActivityLifecycle.ON_DESTROY) {
            disposables.clear()
        }
    }

    fun changeText() {
        message.set(resourceProvider.getString(R.string.anko_first_btn_change_text))
    }

    fun changeTextDelayTime() {
        Observable.timer(3, TimeUnit.SECONDS, schedulerFactory.computation())
            .observeOn(schedulerFactory.ui())
            .doOnNext { message.set(resourceProvider.getString(R.string.anko_first_btn_change_text_delay)) }
            .subscribe()
            .addTo(disposables)
    }
}