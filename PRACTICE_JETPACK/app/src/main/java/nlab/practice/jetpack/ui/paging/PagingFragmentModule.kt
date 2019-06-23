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

package nlab.practice.jetpack.ui.paging

import androidx.fragment.app.Fragment
import androidx.paging.DataSource
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_paging.*
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule

internal typealias DFactory = DataSource.Factory<Int, PagingItemPracticeViewModel>

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
@Module(includes = [
    ChildFragmentModule::class,
    PositionalPagingModule::class
])
class PagingFragmentModule {

    @Provides
    fun providePagingItemViewModelFactory(fragmentNavUsecase: FragmentNavUsecase): PagingItemPracticeViewModelFactory {
        return PagingItemPracticeViewModelFactory{fragmentNavUsecase}
    }

    @Provides
    fun provideRecyclerViewUsecase(fragment: Fragment): RecyclerViewUsecase = RecyclerViewUsecase {
        fragment.lvContents
    }
}