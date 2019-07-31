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

import androidx.fragment.app.FragmentManager

/**
 * @author Doohyun
 */
class PrimaryContainerUsecase(private val fragmentManager: FragmentManager) {

    private val primaryContainer: ContainerFragment?
        get(): ContainerFragment? {
            return fragmentManager.primaryNavigationFragment
                ?.let { it as? ContainerFragment.Owner }
                ?.getContainerDelegate()
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
}