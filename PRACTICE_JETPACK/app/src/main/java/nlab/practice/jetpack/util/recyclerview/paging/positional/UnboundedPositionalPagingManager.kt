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
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.SchedulerFactory

/**
 *  전체 사이즈를 알 수 없는 경우, Position 만으로 데이터를 처리하는 Manager
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
class UnboundedPositionalPagingManager<T>
private constructor(
        private val disposables: CompositeDisposable,
        private val schedulerFactory: SchedulerFactory,
        private val dataRepository: DataRepository<T>) : PositionalPagingManager<T>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        stateSubject.onNext(PositionalEvent(PositionalDataLoadState.LOAD_START, rangeParams = params))

        dataRepository.getItems(params.startPosition, params.loadSize)
                .doOnSuccess {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.LOAD_FINISH, rangeParams = params))
                    callback.onResult(it)
                }
                .observeOn(schedulerFactory.ui())
                .doOnSuccess { clearRetry() }
                .doOnError {
                    // FIXME retry 세팅 시, 외부 ui 스케줄러와 동기화를 맞춰야 하기 때문에 동시에 처리
                    setRetry(params, callback)
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.LOAD_ERROR, rangeParams = params))
                }
                .subscribe()
                .addTo(disposables)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_START, params))

        val startPosition = params.requestedStartPosition.takeIf { it >= 0 }?.run { this } ?: 0

        dataRepository.getItems(startPosition, params.requestedLoadSize)
                .doOnSuccess {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_FINISH, params))
                    callback.onResult(it, startPosition)
                }
                .doOnError {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_ERROR, params))
                }
                .subscribe()
                .addTo(disposables)
    }

    interface DataRepository<T> {
        fun getItems(offset: Int, limit: Int): Single<List<T>>
    }

    class Factory(private val disposables: CompositeDisposable, private val schedulerFactory: SchedulerFactory) {
        fun <T> create(dataRepository: DataRepository<T>): UnboundedPositionalPagingManager<T> {
            return UnboundedPositionalPagingManager(disposables, schedulerFactory, dataRepository)
        }
    }
}