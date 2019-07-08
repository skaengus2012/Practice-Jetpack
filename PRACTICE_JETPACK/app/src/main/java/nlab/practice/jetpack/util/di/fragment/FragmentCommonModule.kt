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
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.FragmentCallback
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.component.lifecycle.LifeCycleBinder
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
    fun provideActivityNavUsecase(fragment: Fragment): ActivityNavUsecase {
        return DefaultActivityNavUsecase(fragment.activity!!)
    }

    @FragmentScope
    @Provides
    fun provideActivityCommonUsecase(fragment: Fragment) = ActivityCommonUsecase(fragment.activity!!)

    @Named(ContextInjectionType.ACTIVITY)
    @FragmentScope
    @Provides
    fun provideIntentProvider(fragment: Fragment) = IntentProvider(fragment.activity!!)

    @Named(ContextInjectionType.FRAGMENT)
    @FragmentScope
    @Provides
    fun provideFragmentOwnerActivityNavUsecase(fragment: Fragment): ActivityNavUsecase {
        return FragmentOwnerActivityNavUsecase(fragment)
    }

    @FragmentScope
    @Provides
    fun provideLifeCycleBinder(): FragmentLifeCycleBinder = LifeCycleBinder()

    @FragmentScope
    @Provides
    fun provideLayoutManagerFactory(fragment: Fragment) = LayoutManagerFactory(fragment.activity!!)

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