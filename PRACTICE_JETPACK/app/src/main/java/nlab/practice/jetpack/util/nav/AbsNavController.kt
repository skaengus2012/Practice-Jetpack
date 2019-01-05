package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Fragment 를 Navigation 할 수 있는 컨트롤러
 *
 * @author Doohyun
 */
abstract class AbsNavController(
        val fragmentManager: FragmentManager,
        @IdRes val fragmentResId: Int) {

    inline fun FragmentManager.findFragmentByTag(
            tag: String,
            fragmentTransaction: FragmentTransaction,
            crossinline fragmentProvider: () -> Fragment): Fragment {

        return findFragmentByTag(tag) ?: fragmentProvider().apply {
            // NOTE : replace 는 Fragment 를 떼고 붙임. add 시, Fragment 자체에는 어떤 영향도 없음
            fragmentTransaction.add(fragmentResId, this, tag)
        }
    }

    /**
     * primaryNavigationFragment 를 변경해야하는가?
     */
    fun FragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment: Fragment): Boolean {
        return primaryNavigationFragment?.let { it != targetFragment } ?: true
    }
}