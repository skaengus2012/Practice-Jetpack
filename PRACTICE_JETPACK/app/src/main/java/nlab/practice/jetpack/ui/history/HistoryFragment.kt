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

package nlab.practice.jetpack.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_history.*
import nlab.practice.jetpack.databinding.FragmentHistoryBinding
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HistoryFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: HistoryViewModel

    lateinit var binding: FragmentHistoryBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHistoryBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @Provides
        fun provideRecyclerViewUsecase(fragment: HistoryFragment): RecyclerViewUsecase = RecyclerViewUsecase {
            fragment.lvContents
        }

        @Provides
        fun provideHistoryItemViewFactory(
                resourceProvider: ResourceProvider): HistoryItemViewModelFactory = HistoryItemViewModelFactory{

            resourceProvider
        }

    }
}