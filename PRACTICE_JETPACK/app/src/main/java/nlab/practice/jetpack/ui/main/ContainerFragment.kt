package nlab.practice.jetpack.ui.main

import nlab.practice.jetpack.util.nav.ChildNavController

/**
 * Fragment 중 자식을 소유한 Fragment 정의
 *
 * @author Doohyun
 */
interface ContainerFragment {
    fun getChildNavController(): ChildNavController
}