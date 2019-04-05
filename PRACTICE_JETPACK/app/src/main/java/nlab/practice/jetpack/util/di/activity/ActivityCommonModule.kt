package nlab.practice.jetpack.util.di.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.RxUtils
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.ActivityCallback
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.component.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import org.jetbrains.anko.contentView

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module
class ActivityCommonModule {

    @ActivityScope
    @Provides
    fun provideDisposables(): CompositeDisposable = RxUtils.createLazyDisposables()

    @ActivityScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): ActivityLifeCycleBinder = LifeCycleBinder(disposables)

    @ActivityScope
    @Provides
    fun provideActivityNavUsecase(activity: Activity): ActivityNavUsecase = ActivityNavUsecase(activity)

    @ActivityScope
    @Provides
    fun provideActivityCommonUsecase(activity: Activity): ActivityCommonUsecase = ActivityCommonUsecase(activity)

    @ActivityScope
    @Provides
    fun provideActivityCallbackBinder(): ActivityCallback = ActivityCallback()

    @ActivityScope
    @Provides
    fun provideLayoutManager(activity: Activity): LayoutManagerFactory = LayoutManagerFactory(activity)

    @ActivityScope
    @Provides
    fun provideSnackBarHelper(activity: Activity) = SnackBarHelper {
        if (!activity.isFinishing) {
            activity.contentView
        } else {
            null
        }
    }
}