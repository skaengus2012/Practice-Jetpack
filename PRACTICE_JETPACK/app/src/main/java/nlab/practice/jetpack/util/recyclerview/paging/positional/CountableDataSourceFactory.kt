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
import io.reactivex.subjects.PublishSubject
import nlab.practice.jetpack.util.SchedulerFactory
import java.lang.Exception

/**
 * @author Doohyun
 * @since 2019. 08. 06
 */
class CountableDataSourceFactory<T>(
    private val repository: Repository<T>,
    private val schedulerFactory: SchedulerFactory
) : DataSourceFactory<T> {

    private val loadEventSubject = PublishSubject.create<PositionalLoadEvent>()

    private var currentTotalCount: Int? = null

    override val loadEventObservable: Observable<PositionalLoadEvent>
        get() = loadEventSubject

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        PositionalLoadEvent(PositionalDataLoadState.INIT_LOAD_START, initParams = params).post()

        repository.totalCountSingle()
            .observeOn(schedulerFactory.ui())
            .doOnSuccess { totalCount ->
                currentTotalCount = totalCount

                if (totalCount == 0) {
                    doOnEmptyStateWhenInitLoading(params, callback)
                } else {
                    doOnHasItemWhenInitLoading(params, callback)
                }
            }
            .doOnError { doOnErrorWhenInitLoading(params) }
            .subscribe()
    }

    private fun doOnEmptyStateWhenInitLoading(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        PositionalLoadEvent(PositionalDataLoadState.INIT_LOAD_FINISH_NO_DATA, initParams = params).post()
        callback.onResult(emptyList(), params.requestedStartPosition, 0)
    }
    
    private fun doOnErrorWhenInitLoading(params: LoadInitialParams) {
        PositionalLoadEvent(PositionalDataLoadState.INIT_LOAD_ERROR, initParams = params).post()
    }

    @Throws(TotalCountNotSettingException::class)
    private fun doOnHasItemWhenInitLoading(
        params: LoadInitialParams,
        callback: LoadInitialCallback<T>
    ) = currentTotalCount?.let { responseTotalCount ->

        val position = computeInitialLoadPosition(params, responseTotalCount)
        val loadSize = computeInitialLoadSize(params, position, responseTotalCount)

        repository.loadSingle(position, loadSize)
            .subscribeOn(schedulerFactory.ui())
            .doOnSuccess {
                val isEqualsTotalCount = (responseTotalCount == it.totalCount)
                if (isEqualsTotalCount) {
                    PositionalLoadEvent(PositionalDataLoadState.INIT_LOAD_FINISH, initParams = params).post()
                    callback.onResult(it.items, position, responseTotalCount)
                } else {
                    PositionalLoadEvent(PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED, initParams = params).post()
                }
            }
            .doOnError { doOnErrorWhenInitLoading(params) }
            .subscribe()

    } ?: errorByMissingTotalCount()

    private fun PositionalLoadEvent.post() {
        loadEventSubject.onNext(this)
    }

    private fun errorByMissingTotalCount(): Nothing = throw TotalCountNotSettingException()

    class TotalCountNotSettingException : Exception()
}