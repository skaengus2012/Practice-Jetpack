package nlab.practice.jetpack.ui.main

import androidx.databinding.BaseObservable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.component.callback.ActivityCallback
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
        activityCallback: ActivityCallback,
        private val _mainNavUsecase: MainBottomNavUsecase): BaseObservable() {

    init {
        activityLifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) { executeOnCreate() }
        activityCallback.onBackPressed(this::executeOnBackPressed)
        activityCallback.onRestoreInstanceState { executeOnRestoreInstanceState() }
    }

    private fun executeOnCreate() = with(_mainNavUsecase) {
        initialize()
        navFirstPage()
    }

    private fun executeOnBackPressed(): Boolean = when {
        // 현재 Primary 화면의 OnBackPressed 의 처리 결과에 따라 이 후 작업 결정
        _mainNavUsecase.onBackPressedInPrimaryNav() -> true

        // 현재 메뉴가 Home 이 아닐 경우 홈으로 이동
        _mainNavUsecase.getSelectedItemId() != R.id.menu_home -> {
            _mainNavUsecase.navFirstPage()
            true
        }

        // 그 외 거짓
        else -> false
    }

    private fun executeOnRestoreInstanceState() = with(_mainNavUsecase) { navPage(getSelectedItemId()) }

}