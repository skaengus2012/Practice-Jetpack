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

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.di.activity.ActivityCommonUsecase
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
import nlab.practice.jetpack.util.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.lifecycle.FragmentSavedStateProvider
import nlab.practice.jetpack.util.lifecycle.LifecycleStateProvider
import nlab.practice.jetpack.util.nav.*
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import javax.inject.Named


/**
 * Fragment 공통 모듈 정의
 *
 * @author Doohyun
 */
@Module
class FragmentCommonModule {

    @FragmentScope
    @Provides
    fun provideFragmentCallback() = FragmentCallback()

    @Named(ContextInjectionType.ACTIVITY)
    @FragmentScope
    @Provides
    fun provideActivityNavController(fragment: Fragment): ActivityNavController {
        return DefaultActivityNavController(fragment.activity!!)
    }

    @FragmentScope
    @Provides
    fun provideActivityNavUsecase(fragment: Fragment): ActivityNavUsecase {
        return ActivityNavUsecaseImpl(fragment.activity!!)
    }

    @FragmentScope
    @Provides
    fun provideActivityCommonUsecase(fragment: Fragment) =
        ActivityCommonUsecase(fragment.activity!!)

    @Named(ContextInjectionType.ACTIVITY)
    @FragmentScope
    @Provides
    fun provideIntentProvider(fragment: Fragment) = IntentProvider(fragment.activity!!)

    @Named(ContextInjectionType.FRAGMENT)
    @FragmentScope
    @Provides
    fun provideFragmentOwnerActivityNavUsecase(fragment: Fragment): ActivityNavController {
        return FragmentOwnerActivityNavController(fragment)
    }

    @FragmentScope
    @Provides
    fun provideLifeCycleBinder(): FragmentLifecycleBinder =
        LifeCycleBinder()

    @FragmentScope
    @Provides
    fun provideLayoutManagerFactory(fragment: Fragment) = LayoutManagerFactory(fragment.activity!!)

    @FragmentScope
    @Provides
    fun provideSavedStateProvider(fragment: Fragment): LifecycleStateProvider {
        return FragmentSavedStateProvider(fragment)
    }

    @FragmentScope
    @Provides
    fun provideSnackBarHelper(
            fragment: Fragment,
            resourceProvider: ResourceProvider) = SnackBarHelper(resourceProvider) {
        fragment.activity
                ?.takeUnless { it.isFinishing }
                ?.run { findViewById(android.R.id.content) }
    }
}