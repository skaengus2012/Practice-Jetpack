package nlab.practice.jetpack.di.component

import android.app.Application
import dagger.Component
import nlab.practice.jetpack.di.module.RepositoryModule
import javax.inject.Singleton
import nlab.practice.jetpack.di.module.AppModule
import dagger.BindsInstance

/**
 * @author Doohyun
 */
@Singleton
@Component(modules = [
    AppModule::class,
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
        fun getAppComponent(): AppComponent
    }

    fun viewModelComponent(): ViewModelInjectComponent.Builder
}