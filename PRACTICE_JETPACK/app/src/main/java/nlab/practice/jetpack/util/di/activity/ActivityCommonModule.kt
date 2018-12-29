package nlab.practice.jetpack.util.di.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.ActivityStarterUsecase
import nlab.practice.jetpack.util.createLazyCompositeDisposable
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.lifecycle.LifeCycleBinder

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
class ActivityCommonModule {

    @ActivityScope
    @Provides
    fun provideDisposables(): CompositeDisposable = createLazyCompositeDisposable()

    @ActivityScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): ActivityLifeCycleBinder = LifeCycleBinder(disposables)

    @ActivityScope
    @Provides
    fun provideActivityStarterUsecase(activity: Activity): ActivityStarterUsecase = ActivityStarterUsecase(activity)

}