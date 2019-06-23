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

import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.history.HistoryFragment
import nlab.practice.jetpack.util.lazyPublic
import nlab.practice.jetpack.util.nav.fragmentTag

/**
 * Main 에서 표시된 BottomNavigationView 의 컨트롤러
 *
 * @author Doohyun
 */
class MainBottomNavUsecase(private val navController: MainNavController, viewSupplier: () -> BottomNavigationView) :
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener {

    private val bottomNavigationView: BottomNavigationView by lazyPublic(viewSupplier)

    fun initialize() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener(this)
    }

    fun navFirstPage() {
        navPage(R.id.menu_home)
    }

    fun navPage(@IdRes menuRes: Int) {
        bottomNavigationView.selectedItemId = menuRes

        onNavigationItemSelected(menuRes)
    }

    @IdRes
    fun getSelectedItemId(): Int =bottomNavigationView.selectedItemId

    override fun onNavigationItemSelected(updateMenu: MenuItem) = onNavigationItemSelected(updateMenu.itemId)

    private fun onNavigationItemSelected(@IdRes menuRes: Int): Boolean = when(menuRes) {
        R.id.menu_home -> {
            navController.replacePrimaryFragment(HomeFragment::class.fragmentTag()) {
                HomeFragment()
            }

            true
        }

        R.id.menu_history -> {
            navController.replacePrimaryFragment(HistoryFragment::class.fragmentTag()) {
                HistoryFragment()
            }

            true
        }

        else -> false
    }

    override fun onNavigationItemReselected(updateMenu: MenuItem) {
        navController.getCurrentContainerFragment()
                ?.run {
                    val result = onBottomNavReselected()
                    if (!result) {
                        getChildNavController().clearFragments()
                    }
                }
                ?: run { onNavigationItemSelected(updateMenu.itemId) }
    }

    fun onBackPressedInPrimaryNav(): Boolean = navController.getCurrentContainerFragment()?.onBackPressed()?:false
}