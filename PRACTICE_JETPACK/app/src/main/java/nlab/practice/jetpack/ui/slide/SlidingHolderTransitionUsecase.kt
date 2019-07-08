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

import android.view.View
import androidx.annotation.StringDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

/**
 * @author Doohyun
 */
class SlidingHolderTransitionUsecase(
        private val fragmentManager: FragmentManager,
        private val containerViewSupplier: () -> View
) {

    private val fragmentMap: MutableMap<String, WeakReference<Fragment>> = HashMap()

    private val containerView: View
        get() = containerViewSupplier()

    fun replaceMainFragment() {
        fragmentManager.beginTransaction()
                .replace(containerView.id, resolveMainFragment(), PageType.MAIN)
                .commitNow()
    }

    private fun resolveMainFragment() = fragmentMap[PageType.MAIN]?.get() ?: createMainFragment()

    private fun createMainFragment() = SlidingMainFragment().apply {
        fragmentMap[PageType.MAIN] = WeakReference<Fragment>(this)
    }

    @StringDef(value = [PageType.MAIN])
    annotation class PageType {
        companion object {
            const val MAIN = "MAIN"
        }
    }
}