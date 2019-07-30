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

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.BaseFragment
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.nav.ChildNavController

/**
 * Fragment 중 자식을 소유한 Fragment 정의
 *
 * Fragment 가 Container 로써 해야할 역할을 대신하는 Delegate 를 지원한다.
 *
 * @author Doohyun
 */
interface ContainerFragment {
    val childNavController: ChildNavController
    fun onBottomNavReselected(): Boolean
    fun onBackPressed(): Boolean

    interface Owner {
        fun getContainerDelegate(): ContainerFragment
    }
}

class ContainerFragmentImpl(
    private val fragment: Fragment,
    private val _childNavController: ChildNavController,
    private val callback: ContainerFragmentCallback
) : ContainerFragment {

    override val childNavController: ChildNavController
        get() = _childNavController

    override fun onBottomNavReselected(): Boolean = callback.bottomNavReselectedCommand?.invoke()?:false

    override fun onBackPressed(): Boolean {
        val currentPrimaryOnBackPressedResult = childNavController.getPrimaryNavFragment()
            ?.let { it as? BaseFragment }
            ?.run { onBackPressed() }?: false

        return if (currentPrimaryOnBackPressedResult) {
            true
        } else {
            (fragment as? BaseFragment)?.onBackPressed() ?: false
        }
    }
}

class ContainerFragmentCallback {
    var bottomNavReselectedCommand: (() -> Boolean)? = null
    private set

    fun onBottomNavReselected(callback: () -> Boolean) {
        bottomNavReselectedCommand = callback
    }
}

@Module
class ContainerFragmentModule {

    @FragmentScope
    @Provides
    fun provideContainerFragmentCallback() = ContainerFragmentCallback()

    @FragmentScope
    @Provides
    fun provideContainerFragment(
        fragment: Fragment,
        navController: ChildNavController,
        containerFragmentCallback: ContainerFragmentCallback
    ): ContainerFragment {
        return ContainerFragmentImpl(fragment, navController, containerFragmentCallback)
    }
}



