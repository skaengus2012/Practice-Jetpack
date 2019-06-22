package nlab.practice.jetpack.util.component

import android.app.Activity

/**
 * Activity 기본 유스케이스 정의
 *
 * @author Doohyun
 */
class ActivityCommonUsecase(private val activity: Activity) {

    fun onBackPressed() {
        activity.onBackPressed()
    }

    fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        activity.overridePendingTransition(enterAnim, exitAnim)
    }
}