package nlab.practice.jetpack.common.di.activity

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
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
    fun provideCompositeDisposables(): CompositeDisposable {
        val result : CompositeDisposable by lazy {
            CompositeDisposable()
        }

        return result
    }

    @ActivityScope
    @Provides
    fun provideRxBinder(disposable: CompositeDisposable): ActivityLifeCycleBinder = LifeCycleBinder(disposable)

}