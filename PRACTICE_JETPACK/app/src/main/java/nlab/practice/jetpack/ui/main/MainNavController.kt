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

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import nlab.practice.jetpack.ui.history.HistoryFragment
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.util.nav.findFragmentByTag
import nlab.practice.jetpack.util.nav.fragmentTag
import nlab.practice.jetpack.util.nav.isNeedChangePrimaryNavigationFragment

/**
 * @author Doohyun
 * @since 2019. 07. 30
 */
class MainNavController(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerResId: Int
) {
    private val primaryContainer: ContainerFragment?
        get(): ContainerFragment? {
            return fragmentManager.primaryNavigationFragment
                ?.let { it as? ContainerFragment.Owner }
                ?.getContainerDelegate()
        }

    fun navHome() {
        replacePrimaryFragment(HomeFragment::class.fragmentTag()) { HomeFragment() }
    }

    fun navHistory() {
        replacePrimaryFragment(HistoryFragment::class.fragmentTag()) { HistoryFragment() }
    }

    fun invokeContainerReselected(): Boolean {
        return primaryContainer?.onBottomNavReselected() ?: false
    }

    fun clearContainerChildren() {
        primaryContainer?.childNavController?.clearFragments()
    }

    fun invokeContainerBackPressed(): Boolean {
        return primaryContainer?.onBackPressed() ?: false
    }

    private inline fun replacePrimaryFragment(
        tag: String,
        crossinline fragmentProvider: () -> Fragment
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment = fragmentManager.findFragmentByTag(
            containerResId,
            tag,
            fragmentTransaction, fragmentProvider
        )

        // 현재 Primary Nav 가 보여야할 Target 과 다르면 숨김처리 수행
        if (fragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment)) {
            fragmentManager.primaryNavigationFragment?.run { fragmentTransaction.hide(this) }

            /**
             * NOTE
             *
             * commitNow 실행 시, backstack 사용 불가 -> Primary 사용이니 이 메소드를 호출
             * Activity 에서 실행해야하기 때문에, StateLoss 사용 -> onSaveInstanceState 의 제약에서 벗어나기 위함
             */
            fragmentTransaction
                .show(targetFragment)
                .setPrimaryNavigationFragment(targetFragment)
                .setReorderingAllowed(true)
                .commitNowAllowingStateLoss()
        }
    }

}