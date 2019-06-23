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

package nlab.practice.jetpack.ui.main

import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.activity.ActivityScope

/**
 * Main 에서 제공해야항 모듈 정의
 *
 * @author Doohyun
 */
@Module
class MainHolderModule {

    @ActivityScope
    @Provides
    fun provideNavController(activity: MainHolderActivity): MainNavController {
        return MainNavController(activity.supportFragmentManager,  R.id.layout_container)
    }

    @ActivityScope
    @Provides
    fun provideBottomNavUsecase(activity: MainHolderActivity, navController: MainNavController): MainBottomNavUsecase {
        return MainBottomNavUsecase(navController) { activity.bottom_navigation }
    }
}