package nlab.practice.jetpack.ui.main

import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.nav.FragmentNavUsecase

/**
 * Main 에서 제공해야항 모듈 정의
 *
 * @author Doohyun
 */
@Module
class MainHolderModule {

    @ActivityScope
    @Provides
    fun provideNavController(activity: MainHolderActivity): MainNavController {
        return MainNavController(activity.supportFragmentManager,  R.id.layout_container)
    }

    @ActivityScope
    @Provides
    fun provideBottomNavUsecase(
            activity: MainHolderActivity,
            navController: MainNavController,
            fragmentNavUsecase: FragmentNavUsecase): MainBottomNavUsecase {
        return MainBottomNavUsecase(navController, fragmentNavUsecase) { activity.bottom_navigation }
    }

    @ActivityScope
    @Provides
    fun provideFragmentNavUsecase(activity: MainHolderActivity): FragmentNavUsecase = FragmentNavUsecase {
        activity.supportFragmentManager
                .primaryNavigationFragment
                ?.let { it as? ContainerFragment }
                ?.getChildNavController()
    }
}