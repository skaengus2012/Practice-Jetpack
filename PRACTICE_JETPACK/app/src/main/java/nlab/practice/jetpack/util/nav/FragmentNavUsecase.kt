package nlab.practice.jetpack.util.nav

import nlab.practice.jetpack.ui.introduce.IntroduceFragment
import nlab.practice.jetpack.ui.paging.PagingFragment

/**
 * @author Doohyun
 */
class FragmentNavUsecase(private val _navControllerGetter: () -> ChildNavController?) {

    private fun getNavController() : ChildNavController? = _navControllerGetter()

    fun popBackStack() {
        getNavController()?.popBackStack()
    }

    fun clearFragments(): Boolean = getNavController()?.clearFragments() ?: true

    fun navPaging() {
        getNavController()?.addFragment(PagingFragment::class.fragmentTag()) { PagingFragment() }
    }

    fun navIntroduce() {
        getNavController()?.addFragment(IntroduceFragment::class.fragmentTag()) { IntroduceFragment() }
    }
}