package nlab.practice.jetpack.util.nav

import nlab.practice.jetpack.ui.introduce.Introduce2Fragment

/**
 * @author Doohyun
 */
class FragmentNavUsecase(private val _navController: ChildNavController) {

    fun navIntroduce2() {
        _navController.addFragment(Introduce2Fragment::class.fragmentTag()) { Introduce2Fragment() }
    }
}