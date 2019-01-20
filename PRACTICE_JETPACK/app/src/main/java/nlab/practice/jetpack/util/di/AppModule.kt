package nlab.practice.jetpack.util.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.repository.RepositoryModule
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.SchedulerFactoryImpl
import nlab.practice.jetpack.util.di.activity.ActivityBindComponent
import nlab.practice.jetpack.util.di.fragment.FragmentBindComponent
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
}