package nlab.practice.jetpack.util.component

import android.app.Activity

/**
 * Activity 기본 유스케이스 정의
 *
 * @author Doohyun
 */
class ActivityCommonUsecase(private val _activity: Activity) {

    fun onBackPressed() {
        _activity.onBackPressed()
    }
}