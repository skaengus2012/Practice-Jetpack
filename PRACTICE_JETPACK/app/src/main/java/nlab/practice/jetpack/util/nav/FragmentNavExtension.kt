package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.reflect.KClass

/**
 * Fragment Navigation 유지를 위해 필요한 함수
 *
 * @author Doohyun
 */

/**
 * 클래스의 이름으로 태그를 생성한다.
 */
fun <T : Fragment> KClass<T>.fragmentTag(): String = this.java.name

/**
 * [tag] 로 [containerResId] 에 포함된 Fragment 를 찾는따.
 *
 * 만약 Fragment 가 없다면, [defaultProvider] 로부터 Fragment 를 생성하여 add 한다.
 */
inline fun FragmentManager.findFragmentByTag(
        @IdRes containerResId: Int,
        tag: String,
        fragmentTransaction: FragmentTransaction,
        crossinline defaultProvider: () -> Fragment): Fragment {

    return findFragmentByTag(tag) ?: defaultProvider().apply {
        // NOTE : replace 는 Fragment 를 떼고 붙임. add 시, Fragment 자체에는 어떤 영향도 없음
        fragmentTransaction.add(containerResId, this, tag)
    }
}

/**
 * primaryNavigationFragment 를 변경해야하는가?
 */
fun FragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment: Fragment): Boolean {
    return primaryNavigationFragment?.let { it != targetFragment } ?: true
}

