package nlab.practice.jetpack.ui.home

import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_home.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.main.ContainerFragmentModule
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.nav.ChildNavController
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase

/**
 * Home 에서 제공해줘야 하는 Module
 *
 * @author Doohyun
 */
@Module(includes = [ContainerFragmentModule::class])
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

    @FragmentScope
    @Provides
    fun provideRecyclerViewUsecase(fragment: HomeFragment) : RecyclerViewUsecase = RecyclerViewUsecase {
        fragment.lvContents
    }
}