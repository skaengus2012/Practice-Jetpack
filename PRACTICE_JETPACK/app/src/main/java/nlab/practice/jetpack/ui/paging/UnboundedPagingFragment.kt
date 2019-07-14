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

package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
class UnboundedPagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: UnboundedPagingViewModel

    lateinit var binding: FragmentPagingBinding

    override fun onCreateBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPagingBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}