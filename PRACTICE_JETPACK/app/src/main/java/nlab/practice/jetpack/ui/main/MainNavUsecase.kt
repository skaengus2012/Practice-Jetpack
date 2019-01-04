package nlab.practice.jetpack.ui.main

import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.introduce.IntroduceFragment
import nlab.practice.jetpack.util.nav.NavController
import javax.inject.Inject

/**
 * 메인에서 Navigate 행위를 수행하는 케이스 정리
 *
 * @author Doohyun
 */
class MainNavUsecase @Inject constructor(private val _navController: NavController) {

    fun navHome() {
        _navController.replacePrimaryFragment(HomeFragment::class.java.name) {
            HomeFragment()
        }
    }

    fun navSetting() {
        _navController.replacePrimaryFragment(IntroduceFragment::class.java.name) {
            IntroduceFragment()
        }
    }
}