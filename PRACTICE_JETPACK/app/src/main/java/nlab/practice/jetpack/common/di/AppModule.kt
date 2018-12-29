package nlab.practice.jetpack.common.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.di.module.RepositoryModule
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Singleton

/**
 * @author Doohyun
 */
@Module(subcomponents = [ViewModelInjectComponent::class],
        includes = [RepositoryModule::class, BundleModule::class]
)
class AppModule {

    @Deprecated("ViewModel 에 더이상 주입 시키지 않을 예정")
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.baseContext

    @Singleton
    @Provides
    fun provideResource(application: Application): ResourceProvider = ResourceProvider(application.applicationContext)
}