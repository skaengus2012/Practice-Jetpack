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

interface DataSourceManager<T> {
    fun loadRange(
        params: PositionalDataSource.LoadRangeParams,
        callback: PositionalDataSource.LoadRangeCallback<T>
    )

    fun loadInitial(
        params: PositionalDataSource.LoadInitialParams,
        callback: PositionalDataSource.LoadInitialCallback<T>
    )

    fun createDataSource(): PositionalDataSource<T> = PositionalDataSourceEx(this)
}

class PositionalDataSourceEx<T>(private val dataSourceManager: DataSourceManager<T>) : PositionalDataSource<T>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        dataSourceManager.loadRange(params, callback)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        dataSourceManager.loadInitial(params, callback)
    }
}