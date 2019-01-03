package nlab.practice.jetpack.util.di.fragment

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder

/**
 * Fragment Binding 을 담당하는 Component
 *
 * @author Doohyun
 */
@Subcomponent(modules = [
    FragmentBindModule::class
])
interface FragmentBindComponent {

    fun activityInjector(): DispatchingAndroidInjector<Fragment>

    /**
     * 공통으로 써야하는 정보가 있다면 이 곳에서 파라미터로 받을 것.
     */
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setActivityLifeCycleBinder(activityLifeCycleBinder: ActivityLifeCycleBinder): Builder

        fun build() : FragmentBindComponent
    }
}