package nlab.practice.jetpack.util.di.component

import android.app.Application
import dagger.Component
import nlab.practice.jetpack.util.di.module.RepositoryModule
import javax.inject.Singleton
import nlab.practice.jetpack.util.di.module.AppModule
import dagger.BindsInstance
import dagger.android.AndroidInjectionModule

/**
 * @author Doohyun
 */
@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    RepositoryModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    interface Supplier {
        fun getAppComponent() : AppComponent
    }

    fun viewModelComponent(): ViewModelInjectComponent.Builder
}