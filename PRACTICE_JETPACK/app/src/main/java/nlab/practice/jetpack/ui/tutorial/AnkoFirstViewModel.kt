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
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstViewModel @Inject constructor(
        private val schedulerFactory: SchedulerFactory,
        ankoFirstDataBundle: AnkoFirstDataBundle,
        lifeCycleBinder: ActivityLifeCycleBinder,
        resourceProvider: ResourceProvider) {

    private val disposables = CompositeDisposable()

    val message : ObservableField<String> = ObservableField()

    init {
        (ankoFirstDataBundle.message?: resourceProvider.getString(R.string.anko_first_message)).run {
            message.set(toString())
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            ankoFirstDataBundle.message = null
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_DESTROY) {
            message.get()?.run { ankoFirstDataBundle.message = this }
            disposables.clear()
        }
    }


    fun changeTextDelayTime(message: String, second: Long = 0L) {
        Observable.timer(second, TimeUnit.SECONDS)
                .observeOn(schedulerFactory.ui())
                .doOnNext { this.message.set(message) }
                .subscribe()
                .addTo(disposables)
    }
}