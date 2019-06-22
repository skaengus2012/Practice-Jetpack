package nlab.practice.jetpack.ui.slide

import android.view.View
import androidx.annotation.StringDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import nlab.practice.jetpack.util.lazyPublic
import java.lang.ref.WeakReference

/**
 * @author Doohyun
 */
class SlidingHolderTransitionUsecase(private val fragmentManager: FragmentManager, containerViewSupplier: () -> View) {

    private val fragmentMap: MutableMap<String, WeakReference<Fragment>> = HashMap()

    private val containerView: View by lazyPublic(containerViewSupplier)

    fun replaceMainFragment() {
        fragmentManager.beginTransaction()
                .replace(containerView.id, resolveMainFragment(), PageType.MAIN)
                .commitNow()
    }

    private fun resolveMainFragment() = fragmentMap[PageType.MAIN]?.get() ?: createMainFragment()

    private fun createMainFragment() = SlidingMainFragment().apply {
        fragmentMap[PageType.MAIN] = WeakReference<Fragment>(this)
    }

    @StringDef(value = [PageType.MAIN])
    annotation class PageType {
        companion object {
            const val MAIN = "MAIN"
        }
    }
}