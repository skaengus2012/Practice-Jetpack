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

import nlab.practice.jetpack.ui.centerscroll.CenterScrollerFragment
import nlab.practice.jetpack.ui.itemtouch.ItemTouchHelperFragment
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleFragment
import nlab.practice.jetpack.ui.paging.CountablePagingFragment
import nlab.practice.jetpack.ui.paging.UnboundedPagingFragment
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 */
class FragmentNavUsecase(navSupplier: () -> ChildNavController?) {

    private val navController: ChildNavController? by lazyPublic(navSupplier)

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun clearFragments(): Boolean = navController?.clearFragments() ?: true

    fun hasChild(): Boolean = navController?.hasChild() ?: false

    fun navCountablePaging() {
        navController?.addFragment(CountablePagingFragment::class.fragmentTag()) { CountablePagingFragment() }
    }

    fun navUnboundedPaging() {
        navController?.addFragment(UnboundedPagingFragment::class.fragmentTag()) { UnboundedPagingFragment() }
    }

    fun navListAdapterExample() {
        navController?.addFragment(ListAdapterExampleFragment::class.fragmentTag()) { ListAdapterExampleFragment() }
    }

    fun navDragDrop() {
        navController?.addFragment(ItemTouchHelperFragment::class.fragmentTag()) { ItemTouchHelperFragment()}
    }

    fun navCenterScrolling() {
        navController?.addFragment(CenterScrollerFragment::class.fragmentTag()) { CenterScrollerFragment() }
    }
}