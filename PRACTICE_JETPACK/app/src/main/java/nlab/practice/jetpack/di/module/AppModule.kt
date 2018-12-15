package nlab.practice.jetpack.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import javax.inject.Singleton

/**
 * @author Doohyun
 */
@Module(subcomponents = [ViewModelInjectComponent::class])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application) : Context = application.baseContext
}