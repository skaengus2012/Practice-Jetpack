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

package nlab.practice.jetpack.ui.collapsingtoolbar

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivityCollapsingToolbarBinding
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * CollapsingToolbar 에 대한 연습 화면
 *
 * @author Doohyun
 */
class CollapsingToolbarActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: CollapsingToolbarViewModel

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val binding: ActivityCollapsingToolbarBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_collapsing_toolbar)
        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @ActivityScope
        @Provides
        fun getItemFactory() = CollapsingPagingItemViewModelFactory()

        @ActivityScope
        @Provides
        fun getToolbarItemVisibilityUsecase(activity: CollapsingToolbarActivity) = ToolbarItemVisibilityUsecase(
            { activity.appbarLayout },
            { activity.collapsingToolbar }
        )

    }
}