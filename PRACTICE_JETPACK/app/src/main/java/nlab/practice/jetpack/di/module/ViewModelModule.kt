package nlab.practice.jetpack.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.di.scope.ViewModelLifeCycle

/**
 * @author Doohyun
 */
@Module
class ViewModelModule {

    @ViewModelLifeCycle
    @Provides
    fun provideDisposable() : CompositeDisposable {
        val result : CompositeDisposable by lazy {
            CompositeDisposable()
        }

        return result
    }
}