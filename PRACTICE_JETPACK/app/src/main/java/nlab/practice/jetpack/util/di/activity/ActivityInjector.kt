package nlab.practice.jetpack.util.di.activity

import android.app.Activity
import dagger.android.AndroidInjector


/**
 * Activity 의 Injection 을 처리하는 구현체 정의
 *
 * @author Doohyun
 */
object ActivityInjector : AndroidInjector<Activity> {

    override fun inject(activity: Activity?) {
       activity?.let { it as InjectableActivity }
               ?.getActivityBindComponent()
               ?.activityInjector()
               ?.maybeInject(activity)
    }
}