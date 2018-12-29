package nlab.practice.jetpack.util.di

import android.app.Application
import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import nlab.practice.jetpack.JetPackApplication
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.util.di.activity.ActivityBindComponent
import nlab.practice.jetpack.util.di.fragment.FragmentBindComponent
import nlab.practice.jetpack.util.di.itemview.ItemViewModelFactory

/**
 * @author Doohyun
 */
@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setApplication(application: Application): Builder

        fun build(): AppComponent
    }

    interface Supplier {
        fun getAppComponent(): AppComponent
    }

    fun inject(application: JetPackApplication)

    fun activityBindComponent(): ActivityBindComponent.Builder

    fun fragmentBindComponent(): FragmentBindComponent.Builder

    fun viewModelFactory(): ItemViewModelFactory.Builder

    fun viewModelComponent(): ViewModelInjectComponent.Builder
}