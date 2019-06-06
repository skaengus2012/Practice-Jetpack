package nlab.practice.jetpack.util.di.fragment

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector

/**
 * Fragment 의 Injection 을 처리하는 구현체 정의
 *
 * @author Doohyun
 */
object FragmentInjector : AndroidInjector<Fragment> {

    override fun inject(fragment: Fragment?) {
        fragment?.let { it as InjectableFragment }
                ?.fragmentBindComponent
                ?.activityInjector()
                ?.maybeInject(fragment)
    }

}