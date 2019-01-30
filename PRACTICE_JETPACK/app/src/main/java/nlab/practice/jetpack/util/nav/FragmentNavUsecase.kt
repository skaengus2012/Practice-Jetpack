package nlab.practice.jetpack.util.nav

import nlab.practice.jetpack.ui.paging.CountablePagingFragment
import nlab.practice.jetpack.ui.paging.UnboundedPagingFragment

/**
 * @author Doohyun
 */
class FragmentNavUsecase(private val _navControllerGetter: () -> ChildNavController?) {

    private fun getNavController() : ChildNavController? = _navControllerGetter()

    fun popBackStack() {
        getNavController()?.popBackStack()
    }

    fun clearFragments(): Boolean = getNavController()?.clearFragments() ?: true

    fun hasChild(): Boolean = getNavController()?.hasChild()?:false

    fun navCountablePaging() {
        getNavController()?.addFragment(CountablePagingFragment::class.fragmentTag()) { CountablePagingFragment() }
    }

    fun navUnboundedPaging() {
        getNavController()?.addFragment(UnboundedPagingFragment::class.fragmentTag()) { UnboundedPagingFragment() }
    }
}