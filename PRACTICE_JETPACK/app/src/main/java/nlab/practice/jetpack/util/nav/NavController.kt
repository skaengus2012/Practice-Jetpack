package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.annotation.StringDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Fragment 를 Navigation 할 수 있는 컨트롤러
 *
 * @author Doohyun
 */
class NavController(
        val fragmentManager: FragmentManager,
        @IdRes val fragmentResId: Int) {

    @StringDef(value = [Named.DEFAULT, Named.CHILD])
    annotation class Named {
        companion object {
            private const val TAG = "NavController"

            const val DEFAULT = "${TAG}_default"
            const val CHILD = "${TAG}_child"
        }
    }

    inline fun replacePrimaryFragment(tag: String, crossinline fragmentProvider: () -> Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        val targetFragment: Fragment = fragmentManager.findFragmentByTag(tag) ?: fragmentProvider().apply {
            fragmentTransaction.add(fragmentResId, this, tag)
        }

        // 현재 Primary Nav 가 보여야할 Target 과 다르면 숨김처리 수행
        val isNeedChangePrimary = fragmentManager.primaryNavigationFragment?.let { it != targetFragment } ?: true
        if (isNeedChangePrimary) {
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
}