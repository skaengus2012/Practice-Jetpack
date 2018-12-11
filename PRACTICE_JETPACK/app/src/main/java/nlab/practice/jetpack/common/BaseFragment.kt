package nlab.practice.jetpack.common

import androidx.fragment.app.Fragment
import nlab.practice.jetpack.LeakCanaryWatcher

/**
 * @author Doohyun
 * @since 2018. 12. 11
 */
abstract class BaseFragment : Fragment() {

    override fun onDestroyView() {
        LeakCanaryWatcher.watch(this)

        super.onDestroyView()
    }
}