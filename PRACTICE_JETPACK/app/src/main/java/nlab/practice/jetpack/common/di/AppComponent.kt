package nlab.practice.jetpack.common.di

import android.app.Application
import dagger.Component
import nlab.practice.jetpack.di.module.RepositoryModule
import javax.inject.Singleton
import dagger.BindsInstance
import dagger.android.AndroidInjectionModule
import nlab.practice.jetpack.JetPackApplication
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.common.di.activity.ActivityBinder

/**
 * @author Doohyun
 */
@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    ActivityBinder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    interface Supplier {
        fun getAppComponent(): AppComponent
    }

    fun inject(application: JetPackApplication)

    fun viewModelComponent(): ViewModelInjectComponent.Builder
}