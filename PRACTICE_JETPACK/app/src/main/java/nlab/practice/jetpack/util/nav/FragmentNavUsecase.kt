package nlab.practice.jetpack.util.nav

import nlab.practice.jetpack.ui.introduce.Introduce2Fragment

/**
 * @author Doohyun
 */
class FragmentNavUsecase(private val _navControllerGetter: () -> ChildNavController?) {

    private fun getNavController() : ChildNavController? = _navControllerGetter()

    fun popBackStack() {
        getNavController()?.popBackStack()
    }

    fun clearFragments() {
        getNavController()?.clearFragments()
    }

    fun navIntroduce2() {
        getNavController()?.addFragment(Introduce2Fragment::class.fragmentTag()) { Introduce2Fragment() }
    }
}