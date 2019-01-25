package nlab.practice.jetpack.ui.main

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.nav.FragmentNavUsecase

/**
 * ContainerFragment 가 소유한 자식에게 주어야할 모듈
 *
 * @author Doohyun
 */
@Module
class ChildFragmentModule {

    @FragmentScope
    @Provides
    fun provideFragmentNavUsecase(fragment: Fragment): FragmentNavUsecase = FragmentNavUsecase {
        fragment.activity
                ?.let { it as? MainHolderActivity }
                ?.supportFragmentManager
                ?.primaryNavigationFragment
                ?.let { it as? ContainerFragment.Owner }
                ?.getDelegate()
                ?.getChildNavController()
    }
}