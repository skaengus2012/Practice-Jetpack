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

package nlab.practice.jetpack.util.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.itemtouch.ItemTouchHelperFragment
import nlab.practice.jetpack.ui.history.HistoryFragment
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.home.HomeModule
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleFragment
import nlab.practice.jetpack.ui.paging.CountablePagingFragment
import nlab.practice.jetpack.ui.paging.PagingFragmentModule
import nlab.practice.jetpack.ui.paging.UnboundedPagingFragment
import nlab.practice.jetpack.ui.slide.SlidingControlFragment
import nlab.practice.jetpack.ui.slide.SlidingHolderFragment
import nlab.practice.jetpack.ui.slide.SlidingMainFragment
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelFragmentModule

/**
 * Fragment 를 Binding 하기 위한 모듈
 *
 * @author Doohyun
 */
@Module
abstract class FragmentBindModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        HomeModule::class
    ])
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        HistoryFragment.Module::class
    ])
    abstract fun historyFragment(): HistoryFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        PagingFragmentModule::class
    ])
    abstract fun countablePagingFragment(): CountablePagingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        PagingFragmentModule::class
    ])
    abstract fun unboundedPagingFragment(): UnboundedPagingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        ListAdapterExampleFragment.Module::class
    ])
    abstract fun listAdapterExampleFragment(): ListAdapterExampleFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        ItemTouchHelperFragment.Module::class
    ])
    abstract fun itemTouchHelperFragment(): ItemTouchHelperFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        SlidingHolderFragment.Module::class,
        SlidingUpPanelFragmentModule::class
    ])
    abstract fun slidingHolderFragment(): SlidingHolderFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        SlidingUpPanelFragmentModule::class
    ])
    abstract fun slidingControlFragment(): SlidingControlFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        SlidingUpPanelFragmentModule::class
    ])
    abstract fun slidingMainFragment(): SlidingMainFragment
}