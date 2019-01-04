package nlab.practice.jetpack.util.nav

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.di.activity.ActivityScope
import javax.inject.Named

/**
 * @author Doohyun
 */
@Module
class ActivityScopeNavModule {

    @Named(NavController.Named.DEFAULT)
    @ActivityScope
    @Provides
    fun provideNavController(activity: AppCompatActivity): NavController = NavController(activity.supportFragmentManager)
}