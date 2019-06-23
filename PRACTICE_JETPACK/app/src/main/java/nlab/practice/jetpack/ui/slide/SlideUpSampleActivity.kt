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

package nlab.practice.jetpack.ui.slide

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.Provides
import kotlinx.android.synthetic.main.activity_slide_up_sample.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivitySlideUpSampleBinding
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemViewModelFactory
import nlab.practice.jetpack.util.ViewSupplier
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelActivity
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelActivityModule
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleActivity : InjectableActivity(), SlidingUpPanelActivity.Owner {

    @Inject
    lateinit var viewModel: SlideUpSampleViewModel

    @Inject
    lateinit var slidingUpPanelActivity: SlidingUpPanelActivity

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val binding: ActivitySlideUpSampleBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_slide_up_sample)

        binding.viewModel = viewModel
    }

    override fun getSlidingUpPanelDelegate(): SlidingUpPanelActivity = slidingUpPanelActivity

    @dagger.Module(includes = [SlidingUpPanelActivityModule::class])
    class Module {

        @ActivityScope
        @Provides
        fun provideSlidingUpPanelLayout(activity: Activity) = ViewSupplier { activity.slidingLayout }

        @ActivityScope
        @Provides
        fun provideItemViewModelFactory(): ListAdapterExampleItemViewModelFactory {
            return ListAdapterExampleItemViewModelFactory()
        }
    }
}