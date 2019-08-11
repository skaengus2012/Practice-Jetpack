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
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.paging.PagedListItemPresenter
import nlab.practice.jetpack.util.recyclerview.paging.PagedListPresenterTypeTransformer

/**
 * @author Doohyun
 */
class PagingUsecase<T> private constructor(
    private val schedulerFactory: SchedulerFactory,
    private val dataSourceFactory: DataSourceFactory<T>,
    private val transformer: PagedListPresenterTypeTransformer<T>
) {

    val loadEventObservable: Observable<PositionalLoadEvent>
        get() = dataSourceFactory.loadEventObservable

    private var latestDataSource: InternalPositionalDataSource<T>? = null

    fun createPagedListFlowable(config: PagedList.Config): Flowable<PagedList<PagedListItemPresenter<T>>> {
        return RxPagedListBuilder(createDataSourceTransformerFactory(), config)
            .setFetchScheduler(schedulerFactory.io())
            .setNotifyScheduler(schedulerFactory.ui())
            .buildFlowable(BackpressureStrategy.BUFFER)
    }

    fun invalidate() {
        latestDataSource?.invalidate()
    }

    fun retryLoadRange() {
        latestDataSource?.retryLoadRange()
    }

    private fun createDataSourceTransformerFactory() = object : DataSource.Factory<Int, PagedListItemPresenter<T>>() {

        override fun create(): DataSource<Int, PagedListItemPresenter<T>> {
            return dataSourceFactory.createDataSource()
                .apply { latestDataSource = this }
                .map { transformer.transform(it) }
        }

    }

    class Factory<T>(
        private val schedulerFactory: SchedulerFactory,
        private val dataSourceFactory: DataSourceFactory<T>
    ) {
        fun create(
            transformer: PagedListPresenterTypeTransformer<T>
        ) = PagingUsecase(
            schedulerFactory,
            dataSourceFactory,
            transformer
        )
    }
}