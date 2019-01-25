package nlab.practice.jetpack.ui.main

import dagger.Module
import dagger.Provides
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

    interface Owner {
        fun getDelegate(): ContainerFragment
    }
}

internal class ContainerFragmentImpl internal constructor(
        private val _childNavController: ChildNavController,
        private val _callback: ContainerFragmentCallback) : ContainerFragment {

    override fun getChildNavController(): ChildNavController = _childNavController

    override fun onBottomNavReselected(): Boolean = _callback.onBottomNavReselectedCommand?.invoke()?:false
}

class ContainerFragmentCallback {
    var onBottomNavReselectedCommand: (() -> Boolean)? = null

    inline fun onBottomNavReselected(crossinline callback: ()-> Boolean) {
        onBottomNavReselectedCommand = {callback()}
    }
}

@Module
class ContainerFragmentModule {

    @FragmentScope
    @Provides
    fun provideContainerFragmentCallback() = ContainerFragmentCallback()

    @FragmentScope
    @Provides
    fun provideContainerFragment(navController: ChildNavController, callback: ContainerFragmentCallback): ContainerFragment {
        return ContainerFragmentImpl(navController, callback)
    }
}



