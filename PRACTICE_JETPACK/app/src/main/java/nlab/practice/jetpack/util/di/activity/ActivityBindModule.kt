package nlab.practice.jetpack.util.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.collapsingtoolbar.CollapsingToolbarActivity
import nlab.practice.jetpack.ui.main.MainHolderActivity
import nlab.practice.jetpack.ui.main.MainHolderModule
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity

/**
 * Activity 공통 모듈 정의
 *
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class,
        MainHolderModule::class
    ])
    abstract fun mainHolerActivity(): MainHolderActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class
    ])
    abstract fun ankoFirstActivity(): AnkoFirstActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class
    ])
    abstract fun collapsingToolbarActivity(): CollapsingToolbarActivity
}