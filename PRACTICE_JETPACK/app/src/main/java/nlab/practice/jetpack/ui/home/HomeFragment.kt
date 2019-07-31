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

package nlab.practice.jetpack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_home.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.FragmentHomeBinding
import nlab.practice.jetpack.ui.main.ContainerFragment
import nlab.practice.jetpack.ui.main.ContainerFragmentModule
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.nav.ChildNavController
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HomeFragment : InjectableFragment(), ContainerFragment.Owner {

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var containerFragment: ContainerFragment

    lateinit var binding: FragmentHomeBinding

    override fun onCreateBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    override fun getContainerDelegate(): ContainerFragment = containerFragment

    @dagger.Module(includes = [ContainerFragmentModule::class])
    class Module {
        @FragmentScope
        @Provides
        fun provideNavController(fragment: HomeFragment): ChildNavController {
            return ChildNavController(fragment.childFragmentManager, R.id.layout_container)
        }

        @FragmentScope
        @Provides
        fun provideNavUsecase(navController: ChildNavController): FragmentNavUsecase = FragmentNavUsecase {
            navController
        }

        @FragmentScope
        @Provides
        fun provideRecyclerViewUsecase(fragment: HomeFragment): RecyclerViewUsecase = RecyclerViewUsecase {
            fragment.lvContents
        }
    }
}