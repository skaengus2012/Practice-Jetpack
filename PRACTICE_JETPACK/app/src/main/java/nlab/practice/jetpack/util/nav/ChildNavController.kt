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

package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Child 에서 활용하는 NavController 정의
 *
 * @author Doohyun
 */
class ChildNavController(val fragmentManager: FragmentManager, @IdRes val containerResId: Int) {

    inline fun addFragment(tag: String, crossinline fragmentProvider: () -> Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment: Fragment =
                fragmentManager.findFragmentByTag(containerResId, tag, fragmentTransaction, fragmentProvider)

        if (fragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment)) {
            fragmentManager.primaryNavigationFragment?.run { fragmentTransaction.hide(this) }


            // addToBackStack 에 Tag 를 넣는 이유 : PopBackStack 등에 활용할 수 있음
            fragmentTransaction
                    .show(targetFragment)
                    .setPrimaryNavigationFragment(targetFragment)
                    .addToBackStack(tag)
                    .setReorderingAllowed(true)
                    .commit()
        }
    }

    fun hasChild(): Boolean = fragmentManager.backStackEntryCount != 0

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun clearFragments(): Boolean = fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    fun getPrimaryNavFragment(): Fragment? = fragmentManager.primaryNavigationFragment
}