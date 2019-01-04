package nlab.practice.jetpack.ui.main

import android.view.MenuItem
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ActivityCallbackBinder
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * MainHolderActivity 의 ViewModel
 *
 * @author Doohyun
 */
class MainHolderViewModel @Inject constructor(
        activityLifeCycleBinder: ActivityLifeCycleBinder,
        activityCallbackBinder: ActivityCallbackBinder,
        private val _mainNavUsecase: MainNavUsecase) {

    init {
        activityLifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            _mainNavUsecase.navHome()
        }

        activityCallbackBinder.onBackPressed {
            false
        }
    }

    fun onBottomNavigationItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.menu_home -> {
            _mainNavUsecase.navHome()
            true
        }

        R.id.menu_setting -> {
            _mainNavUsecase.navSetting()
            true
        }

        else -> false
    }

    fun onBottomNavigationItemReSelected(menuItem: MenuItem) {

    }
}