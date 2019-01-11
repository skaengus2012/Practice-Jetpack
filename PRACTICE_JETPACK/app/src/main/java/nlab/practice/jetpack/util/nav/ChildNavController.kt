package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Child 에서 활용하는 NavController 정의
 *
 * @author Doohyun
 */
class ChildNavController(val fragmentManager: FragmentManager, @IdRes val containerResId: Int) {

    inline fun addFragment(tag: String, crossinline fragmentProvider: () -> Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment: Fragment =
                fragmentManager.findFragmentByTag(containerResId, tag, fragmentTransaction, fragmentProvider)

        // addToBackStack 에 Tag 를 넣는 이유 : PopBackStack 등에 활용할 수 있음
        fragmentTransaction
                .setPrimaryNavigationFragment(targetFragment)
                .addToBackStack(tag)
                .setReorderingAllowed(true)
                .commit()
    }

    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun clearFragments(): Boolean = fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}