package nlab.practice.jetpack.ui.main

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import nlab.practice.jetpack.util.nav.findFragmentByTag

/**
 * Main 에서 사용하는 NavController 정의
 *
 * @author Doohyun
 */
class MainNavController(val fragmentManager: FragmentManager, @IdRes val containerResId: Int) {

    inline fun replacePrimaryFragment(tag: String, crossinline fragmentProvider: () -> Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment: Fragment =
                fragmentManager.findFragmentByTag(containerResId, tag, fragmentTransaction, fragmentProvider)

        // 현재 Primary Nav 가 보여야할 Target 과 다르면 숨김처리 수행
        if (fragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment)) {
            fragmentManager.primaryNavigationFragment?.run { fragmentTransaction.hide(this) }

            /**
             * NOTE
             *
             * commitNow 실행 시, backstack 사용 불가 -> Primary 사용이니 이 메소드를 호출
             * Activity 에서 실행해야하기 때문에, StateLoss 사용 -> onSaveInstanceState 의 제약에서 벗어나기 위함
             */
            fragmentTransaction
                    .show(targetFragment)
                    .setPrimaryNavigationFragment(targetFragment)
                    .setReorderingAllowed(true)
                    .commitNowAllowingStateLoss()
        }
    }

    /**
     * primaryNavigationFragment 를 변경해야하는가?
     */
    fun FragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment: Fragment): Boolean {
        return primaryNavigationFragment?.let { it != targetFragment } ?: true
    }

    fun getCurrentContainerFragment(): ContainerFragment? = fragmentManager.primaryNavigationFragment
            ?.let { it as? ContainerFragment.Owner }
            ?.getDelegate()
}