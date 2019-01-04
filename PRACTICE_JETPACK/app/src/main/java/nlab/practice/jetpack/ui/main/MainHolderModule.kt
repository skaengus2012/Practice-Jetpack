package nlab.practice.jetpack.ui.main

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.nav.NavController

/**
 * Main 에서 제공해야항 모듈 정의
 *
 * @author Doohyun
 */
@Module
class MainHolderModule {

    @ActivityScope
    @Provides
    fun provideNavController(activity: MainHolderActivity): NavController =
            NavController(activity.supportFragmentManager,  R.id.layout_container)
}