package nlab.practice.jetpack.ui.main

import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_main_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.activity.ActivityScope

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
    fun provideBottomNavUsecase(activity: MainHolderActivity, navController: MainNavController): MainBottomNavUsecase {
        return MainBottomNavUsecase(navController) { activity.bottom_navigation }
    }
}