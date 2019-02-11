package nlab.practice.jetpack.ui.main

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.BaseFragment
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.nav.ChildNavController

/**
 * Fragment 중 자식을 소유한 Fragment 정의
 *
 * Fragment 가 Container 로써 해야할 역할을 대신하는 Delegate 를 지원한다.
 *
 * @author Doohyun
 */
interface ContainerFragment {
    fun getChildNavController(): ChildNavController
    fun onBottomNavReselected(): Boolean
    fun onBackPressed(): Boolean

    interface Owner {
        fun getDelegate(): ContainerFragment
    }
}

internal class ContainerFragmentImpl internal constructor(
        private val _fragment: Fragment,
        private val _childNavController: ChildNavController,
        private val _callback: ContainerFragmentCallback) : ContainerFragment {

    override fun getChildNavController(): ChildNavController = _childNavController

    override fun onBottomNavReselected(): Boolean = _callback.onBottomNavReselectedCommand?.invoke()?:false

    override fun onBackPressed(): Boolean {
        val currentPrimaryOnBackPressedResult = _childNavController.getPrimaryNavFragment()
                ?.let { it as? BaseFragment }
                ?.run { onBackPressed() }?: false

        return if (currentPrimaryOnBackPressedResult) {
            true
        } else {
            (_fragment as? BaseFragment)?.onBackPressed() ?: false
        }
    }
}

class ContainerFragmentCallback {
    var onBottomNavReselectedCommand: (() -> Boolean)? = null

    inline fun onBottomNavReselected(crossinline callback: ()-> Boolean) {
        onBottomNavReselectedCommand = {callback()}
    }
}

@Module(includes = [
    HolderFragmentModule::class
])
class ContainerFragmentModule {

    @FragmentScope
    @Provides
    fun provideContainerFragmentCallback() = ContainerFragmentCallback()

    @FragmentScope
    @Provides
    fun provideContainerFragment(
            fragment: Fragment,
            navController: ChildNavController,
            containerFragmentCallback: ContainerFragmentCallback): ContainerFragment {
        return ContainerFragmentImpl(fragment, navController, containerFragmentCallback)
    }
}



