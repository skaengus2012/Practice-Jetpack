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

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivityMainHolderBinding
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * Fragment 들의 Host Activity
 *
 * One-Activity 구조로 다수 Fragment 들을 사용하도록 한다.
 *
 * @author Doohyun
 */
class MainHolderActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: MainHolderViewModel

    lateinit var binding: ActivityMainHolderBinding

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @ActivityScope
        @Provides
        fun provideNavController(activity: MainHolderActivity): MainHolderNavController {
            return MainHolderNavController(activity.supportFragmentManager,  R.id.layout_container)
        }

        @ActivityScope
        @Provides
        fun provideBottomNavUsecase(activity: MainHolderActivity): MainBottomNavigationViewUseCase {
            return MainBottomNavigationViewUseCaseImpl { activity.bottom_navigation }
        }

        @ActivityScope
        @Provides
        fun provideMainNavController(): MainNavController {
            return MainNavController()
        }

    }
}
