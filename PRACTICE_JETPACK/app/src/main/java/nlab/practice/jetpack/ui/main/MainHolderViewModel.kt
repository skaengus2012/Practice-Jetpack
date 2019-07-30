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
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.di.activity.ActivityCallback
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import javax.inject.Inject

/**
 * MainHolderActivity 의 ViewModel
 *
 * @author Doohyun
 */
class MainHolderViewModel @Inject constructor(
    activityLifeCycleBinder: ActivityLifecycleBinder,
    activityCallback: ActivityCallback,
    private val bottomNavigationViewUsecase: MainBottomNavigationViewUsecase,
    private val navController: MainNavController
) {

    private val disposables = CompositeDisposable()

    init {
        activityLifeCycleBinder.bindUntil(ActivityLifecycle.ON_CREATE) { doOnCreate() }
        activityLifeCycleBinder.bindUntil(ActivityLifecycle.ON_DESTROY) { doOnDestroy() }
        activityCallback.onBackPressed { doOnBackPressed() }
        activityCallback.onRestoreInstanceState { doOnRestoreSavedState() }
    }

    private fun doOnCreate() {
        navigateTab(bottomNavigationViewUsecase.selectedItemId)

        subscribeBottomTabEvent()
    }

    private fun subscribeBottomTabEvent() {
        val validItemIds = setOf(MainBottomNavMenuType.MENU_HOME, MainBottomNavMenuType.MENU_HISTORY)

        bottomNavigationViewUsecase.onSelected { it in validItemIds }
            .subscribe { navigateTab(it) }
            .addTo(disposables)

        bottomNavigationViewUsecase.onReSelected()
            .subscribe {
                if (!navController.invokeContainerReselected()) {
                    navController.clearContainerChildren()
                }
            }
            .addTo(disposables)
    }

    private fun navigateTab(@IdRes itemId: Int) {
        when(itemId) {
            MainBottomNavMenuType.MENU_HOME -> navController.navHome()
            MainBottomNavMenuType.MENU_HISTORY -> navController.navHistory()
        }
    }

    private fun doOnDestroy() {
        disposables.clear()
    }

    private fun doOnBackPressed(): Boolean = when {
        navController.invokeContainerBackPressed() -> true

        bottomNavigationViewUsecase.selectedItemId != MainBottomNavMenuType.MENU_HOME -> {
            bottomNavigationViewUsecase.selectedItemId = MainBottomNavMenuType.MENU_HOME
            true
        }

        else -> false
    }

    private fun doOnRestoreSavedState() {
        bottomNavigationViewUsecase.selectedItemId
            .takeIf { it != MainBottomNavMenuType.MENU_HOME }
            ?.let { navigateTab(it) }
    }
}