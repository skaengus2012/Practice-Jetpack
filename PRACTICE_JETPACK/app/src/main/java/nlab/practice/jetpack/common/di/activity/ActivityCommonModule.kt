package nlab.practice.jetpack.common.di.activity

import android.app.Activity
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.common.di.fragment.FragmentBindModule
import nlab.practice.jetpack.util.ActivityStarterUsecase
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.lifecycle.LifeCycleBinder

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module(includes = [FragmentBindModule::class])
class ActivityCommonModule {

    @ActivityScope
    @Provides
    fun provideCompositeDisposables(): CompositeDisposable {
        val result : CompositeDisposable by lazy {
            CompositeDisposable()
        }

        return result
    }

    @ActivityScope
    @Provides
    fun provideLifeCycleBinder(disposable: CompositeDisposable): ActivityLifeCycleBinder = LifeCycleBinder(disposable)

    @ActivityScope
    @Provides
    fun provideActivityStarterUsecase(activity: Activity): ActivityStarterUsecase = ActivityStarterUsecase(activity)

}