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

package nlab.practice.jetpack.util.di.activity

import android.app.Application
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.BaseActivity
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import nlab.practice.jetpack.util.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.lifecycle.ActivitySavedStateProvider
import nlab.practice.jetpack.util.lifecycle.LifecycleStateProvider
import nlab.practice.jetpack.util.nav.*
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import javax.inject.Named

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
class ActivityCommonModule {

    @ActivityScope
    @Provides
    fun provideLifeCycleBinder(): ActivityLifecycleBinder = LifeCycleBinder()

    @Named(ContextInjectionType.ACTIVITY)
    @ActivityScope
    @Provides
    fun provideActivityNavController(activity: BaseActivity): ActivityNavController = DefaultActivityNavController(activity)

    @ActivityScope
    @Provides
    fun provideActivityCommonUsecase(activity: BaseActivity) = ActivityCommonUsecase(activity)

    @ActivityScope
    @Provides
    fun provideLayoutManager(activity: BaseActivity): LayoutManagerFactory = LayoutManagerFactory(activity)

    @ActivityScope
    @Provides
    fun provideSavedStateProvider(activity: BaseActivity): LifecycleStateProvider {
        return ActivitySavedStateProvider(activity)
    }

    @ActivityScope
    @Provides
    fun provideActivityCallback() = ActivityCallback()

    @ActivityScope
    @Provides
    fun provideActivityNavUsecase(activity: BaseActivity): ActivityNavUsecase = ActivityNavUsecaseImpl(activity)

    @ActivityScope
    @Provides
    fun provideSnackBarHelper(
        activity: BaseActivity,
        resourceProvider: ResourceProvider
    ) = SnackBarHelper(resourceProvider) {
        val isNoneFinishing = !activity.isFinishing
        if (isNoneFinishing) {
            activity.findViewById(android.R.id.content)
        } else {
            null
        }
    }
}