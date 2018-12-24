package nlab.practice.jetpack.common.di.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.MainHolderActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class
    ])
    abstract fun mainHolerActivity(): MainHolderActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        ActivityCommonModule::class
    ])
    abstract fun ankoFirstActivity(): AnkoFirstActivity
}