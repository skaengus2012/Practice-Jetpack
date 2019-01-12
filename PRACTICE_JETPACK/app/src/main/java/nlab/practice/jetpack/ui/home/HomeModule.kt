package nlab.practice.jetpack.ui.home

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.nav.ChildNavController
import nlab.practice.jetpack.util.nav.FragmentNavUsecase

/**
 * Home 에서 제공해줘야 하는 Module
 *
 * @author Doohyun
 */
@Module
class HomeModule {

    @FragmentScope
    @Provides
    fun provideHomeItemViewModelFactory(): HomeItemViewModelFactory = HomeItemViewModelFactory()

    @FragmentScope
    @Provides
    fun provideNavController(fragment: HomeFragment): ChildNavController {
        return ChildNavController(fragment.childFragmentManager, R.id.layout_container)
    }

    @FragmentScope
    @Provides
    fun provideNavUsecase(navController: ChildNavController) : FragmentNavUsecase = FragmentNavUsecase {
        navController
    }
}