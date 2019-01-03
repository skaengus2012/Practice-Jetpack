package nlab.practice.jetpack.util.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.di.module.RepositoryModule
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.di.activity.ActivityBindComponent
import nlab.practice.jetpack.util.di.fragment.FragmentBindComponent
import javax.inject.Singleton

/**
 * @author Doohyun
 */
@Module(
        subcomponents = [
            ViewModelInjectComponent::class,
            ActivityBindComponent::class,
            FragmentBindComponent::class
        ],

        includes = [
            RepositoryModule::class,
            BundleModule::class
        ]
)
class AppModule {

    @Deprecated("ViewModel 에 더이상 주입 시키지 않을 예정")
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.baseContext

    @Singleton
    @Provides
    fun provideResource(application: Application): ResourceProvider = ResourceProvider(application.applicationContext)

    @Provides
    fun provideAndroidScheduler(): Scheduler = AndroidSchedulers.mainThread()
}