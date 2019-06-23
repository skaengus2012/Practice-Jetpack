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

package nlab.practice.jetpack.repository

import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Singleton

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun provideTestMenuRepository(resourceProvider: ResourceProvider): TestMenuRepository = TestMenuRepository(resourceProvider)

    @Reusable
    @Provides
    fun provideImagePoolRepository(): ImagePoolRepository = ImagePoolRepository()

    @Singleton
    @Provides
    fun providePagingDataSourceRepository(imagePoolRepository: ImagePoolRepository) : PagingItemRepository = PagingItemRepository(imagePoolRepository)

    @Reusable
    @Provides
    fun provideHistoryRepository(): HistoryRepository = HistoryRepository()

    @Reusable
    @Provides
    fun provideCoverRepository(): CoverRepository = CoverRepository()

    @Singleton
    @Provides
    fun providePlayerRepository(imagePoolRepository: ImagePoolRepository): PlayerRepository = PlayerRepository(imagePoolRepository)
}