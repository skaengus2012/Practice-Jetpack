package nlab.practice.jetpack.util.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.di.scope.ViewModelLifeCycle

/**
 * @author Doohyun
 */
@Module
class ViewModelModule(private val application: Application){

    @ViewModelLifeCycle
    @Provides
    fun provideDisposable() : CompositeDisposable {
        val result : CompositeDisposable by lazy {
            CompositeDisposable()
        }

        return result
    }

    @Provides
    fun provideApplication() : Application = application

    @Provides
    fun provideContext() : Context = application.baseContext
}