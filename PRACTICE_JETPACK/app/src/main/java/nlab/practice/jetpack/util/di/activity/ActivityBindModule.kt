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

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.collapsingtoolbar.CollapsingToolbarActivity
import nlab.practice.jetpack.ui.landscape.LandScapeActivity
import nlab.practice.jetpack.ui.main.MainHolderActivity
import nlab.practice.jetpack.ui.main.MainHolderModule
import nlab.practice.jetpack.ui.slide.SlideUpSampleActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity

/**
 * Activity 공통 모듈 정의
 *
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class,
        MainHolderModule::class
    ])
    abstract fun mainHolerActivity(): MainHolderActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class,
        AnkoFirstActivity.Module::class
    ])
    abstract fun ankoFirstActivity(): AnkoFirstActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class,
        CollapsingToolbarActivity.Module::class
    ])
    abstract fun collapsingToolbarActivity(): CollapsingToolbarActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class,
        SlideUpSampleActivity.Module::class
    ])
    abstract fun slideUpSampleActivity(): SlideUpSampleActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class
    ])
    abstract fun landScapeActivity(): LandScapeActivity
}