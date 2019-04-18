package nlab.practice.jetpack.util.di.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.ActivityCallback
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.component.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.nav.ContextInjectionType
import nlab.practice.jetpack.util.nav.DefaultActivityNavUsecase
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import javax.inject.Named

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
class ActivityCommonModule {

    @ActivityScope
    @Provides
    fun provideDisposables() = CompositeDisposable()

    @ActivityScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): ActivityLifeCycleBinder = LifeCycleBinder(disposables)

    @Named(ContextInjectionType.ACTIVITY)
    @ActivityScope
    @Provides
    fun provideActivityNavUsecase(activity: Activity): ActivityNavUsecase = DefaultActivityNavUsecase(activity)

    @ActivityScope
    @Provides
    fun provideActivityCommonUsecase(activity: Activity) = ActivityCommonUsecase(activity)

    @ActivityScope
    @Provides
    fun provideActivityCallbackBinder(): ActivityCallback = ActivityCallback()

    @ActivityScope
    @Provides
    fun provideLayoutManager(activity: Activity): LayoutManagerFactory = LayoutManagerFactory(activity)

    @ActivityScope
    @Provides
    fun provideSnackBarHelper(activity: Activity, resourceProvider: ResourceProvider) = SnackBarHelper(resourceProvider) {
        val isNoneFinishing = !activity.isFinishing
        if (isNoneFinishing) {
            activity.findViewById(android.R.id.content)
        } else {
            null
        }
    }
}