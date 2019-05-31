package nlab.practice.jetpack.ui.slide

import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleViewModel @Inject constructor(
        lifeCycleBinder: ActivityLifeCycleBinder,
        activityCommonUsecase: ActivityCommonUsecase,
        slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase) {

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            slidingUpPanelLayoutUsecase.initialize()
            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

}