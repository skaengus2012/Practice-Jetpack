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

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import nlab.practice.jetpack.util.SchedulerFactory

/**
 * @author Doohyun
 */
class PositionalPagingUsecase<T> private constructor(
    private val schedulerFactory: SchedulerFactory,
    private val dataSourceFactory: DataSourceFactory<T>,
    private val transformer: (T) -> PositionalPresenter<T>
) {

    val loadEventObservable: Observable<PositionalLoadEvent>
        get() = dataSourceFactory.loadEventObservable

    private var latestDataSource: PositionalDataSource<T>? = null

    fun createPagedListFlowable(config: PagedList.Config): Flowable<PagedList<PositionalPresenter<T>>> {
        return RxPagedListBuilder(createDataSourceTransformerFactory(), config)
            .setFetchScheduler(schedulerFactory.io())
            .setNotifyScheduler(schedulerFactory.ui())
            .buildFlowable(BackpressureStrategy.BUFFER)
    }

    fun invalidate() {
        latestDataSource?.invalidate()
    }

    private fun createDataSourceTransformerFactory() = object : DataSource.Factory<Int, PositionalPresenter<T>>() {

        override fun create(): DataSource<Int, PositionalPresenter<T>> {
            return dataSourceFactory.createDataSource()
                .apply { latestDataSource = this }
                .map { transformer.invoke(it) }
        }

    }

    class Factory<T>(
        private val schedulerFactory: SchedulerFactory,
        private val dataSourceFactory: DataSourceFactory<T>
    ) {
        fun create(
            transformer: (T) -> PositionalPresenter<T>
        ) = PositionalPagingUsecase(schedulerFactory, dataSourceFactory, transformer)
    }

}

interface PositionalPresenter<T> {
    val item: T
}