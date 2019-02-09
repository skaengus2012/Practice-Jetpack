package nlab.practice.jetpack.util

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import nlab.practice.jetpack.LeakCanaryWatcher

/**
 * 기본이 되는 Fragment 정의
 *
 * @author Doohyun
 */
abstract class BaseFragment : Fragment() {

    @CallSuper
    override fun onDestroyView() {
        LeakCanaryWatcher.watch(this)

        super.onDestroyView()
    }

    open fun onBackPressed(): Boolean = false
}