package nlab.practice.jetpack.util.di.activity

import android.app.Activity
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

/**
 * Activity Binding 을 담당하는 Component
 *
 * @author Doohyun
 */
@Subcomponent(modules = [
    ActivityBindModule::class
])
interface ActivityBindComponent {

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    /**
     * 공통으로 써야하는 정보가 있다면 이 곳에서 파라미터로 받을 것.
     */
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setActivity(activity: Activity): Builder

        fun build() : ActivityBindComponent
    }
}