package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.reflect.KClass

/**
 * Child 에서 활용하는 NavController 정의
 *
 * @author Doohyun
 */
class ChildNavController(fm: FragmentManager, @IdRes idRes: Int) : AbsNavController(fm, idRes) {
    fun popBackStack() {
        fragmentManager.popBackStack()
    }

    fun clearFragments(): Boolean = fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    inline fun addFragment(tag: String, crossinline fragmentProvider: () -> Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment: Fragment = fragmentManager.findFragmentByTag(tag, fragmentTransaction, fragmentProvider)

        if (fragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment)) {
            fragmentManager.primaryNavigationFragment?.run { fragmentTransaction.hide(this) }

            // addToBackStack 에 Tag 를 넣는 이유 : PopBackStack 등에 활용할 수 있음
            fragmentTransaction
                    .show(targetFragment)
                    .setPrimaryNavigationFragment(targetFragment)
                    .addToBackStack(tag)
                    .setReorderingAllowed(true)
                    .commit()
        }
    }
}

fun <T: Fragment> KClass<T>.fragmentTag(): String = this.java.name