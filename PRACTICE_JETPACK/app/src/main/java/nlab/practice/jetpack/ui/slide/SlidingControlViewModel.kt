package nlab.practice.jetpack.ui.slide

import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingControlViewModel @Inject constructor(
        private val _slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val _toastHelper: ToastHelper) {

    fun onClickPlayed() {
        _toastHelper.showToast(R.string.slide_up_panel_play_message)
    }

    fun onPanelLayoutClick() {
        _slidingUpPanelLayoutUsecase?.expand()
    }
}