package nlab.practice.jetpack.ui.main

import androidx.databinding.BaseObservable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.component.callback.ActivityCallbackDelegate
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * MainHolderActivity 의 ViewModel
 *
 * @author Doohyun
 */
class MainHolderViewModel @Inject constructor(
        activityLifeCycleBinder: ActivityLifeCycleBinder,
        activityCallbackDelegate: ActivityCallbackDelegate,
        private val _mainNavUsecase: MainBottomNavUsecase): BaseObservable() {

    init {
        activityLifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            _mainNavUsecase.initialize()
        }

        activityCallbackDelegate.onBackPressed(this::executeOnBackPressed)
    }

    private fun executeOnBackPressed(): Boolean = when {
        // 현재 메뉴가 Home 이 아닐 경우 홈으로 이동
        _mainNavUsecase.getSelectedItemId() != R.id.menu_home -> {
            _mainNavUsecase.navHome()
            true
        }

        // 그 외 거짓
        else -> false
    }
}