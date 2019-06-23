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

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.SchedulerFactory

/**
 * Paging 관련 컴포넌트 제공자
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
@Module
class PositionalPagingModule {

    @Provides
    fun provideCountablePositionalPagingManagerFactory(
            disposables: CompositeDisposable, schedulerFactory: SchedulerFactory): CountablePositionalPagingManager.Factory {
        return CountablePositionalPagingManager.Factory(disposables, schedulerFactory)
    }

    @Provides
    fun provideUnboundedPositionalPagingManagerFactory(
            disposables: CompositeDisposable, schedulerFactory: SchedulerFactory): UnboundedPositionalPagingManager.Factory {
        return UnboundedPositionalPagingManager.Factory(disposables, schedulerFactory)
    }
}