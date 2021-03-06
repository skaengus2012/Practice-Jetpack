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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_sliding_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.FragmentSlidingHolderBinding
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * Google music Style
 *
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingHolderFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: SlidingHolderViewModel

    lateinit var binding: FragmentSlidingHolderBinding

    override fun onCreateBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSlidingHolderBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    @dagger.Module
    class Module {

        @FragmentScope
        @Provides
        fun provideSlideHolderViewUsecase(fragment: Fragment): SlidingHolderViewUsecase {
            return SlidingHolderViewUsecase(containerViewSupplier = { fragment.fragmentContainer },
                miniPlayerViewSupplier = { fragment.view?.findViewById(R.id.fragmentSlideControl) })
        }

        @FragmentScope
        @Provides
        fun provideSlidingHolderTransitionUsecase(fragment: Fragment): SlidingHolderTransitionUsecase {
            return SlidingHolderTransitionUsecase(fragment.childFragmentManager) { fragment.fragmentContainer }
        }
    }
}