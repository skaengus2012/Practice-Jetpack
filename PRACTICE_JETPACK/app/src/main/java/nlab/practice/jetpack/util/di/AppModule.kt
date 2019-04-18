package nlab.practice.jetpack.util.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.repository.RepositoryModule
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.SchedulerFactoryImpl
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.di.activity.ActivityBindComponent
import nlab.practice.jetpack.util.di.fragment.FragmentBindComponent
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.nav.ContextInjectionType
import nlab.practice.jetpack.util.nav.DefaultActivityNavUsecase
import nlab.practice.jetpack.util.nav.IntentProvider
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Doohyun
 */
@Module(
        subcomponents = [
            ActivityBindComponent::class,
            FragmentBindComponent::class
        ],

        includes = [
            RepositoryModule::class,
            BundleModule::class
        ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideResource(application: Application): ResourceProvider = ResourceProvider(application.applicationContext)

    @Reusable
    @Provides
    fun provideSchedulerFactory(): SchedulerFactory = SchedulerFactoryImpl()

    @Reusable
    @Provides
    fun provideToastHelper(application: Application): ToastHelper = ToastHelper(application)

    @Named(ContextInjectionType.APPLICATION)
    @Reusable
    @Provides
    fun provideApplicationIntent(application: Application) = IntentProvider(application.baseContext)

    @Named(ContextInjectionType.APPLICATION)
    @Reusable
    @Provides
    fun provideActivityNavUsecae(application: Application): ActivityNavUsecase {
        return DefaultActivityNavUsecase (application.baseContext)
    }
}