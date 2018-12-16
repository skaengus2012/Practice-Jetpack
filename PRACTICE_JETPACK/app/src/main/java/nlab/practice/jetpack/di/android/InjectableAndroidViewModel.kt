package nlab.practice.jetpack.di.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import nlab.practice.jetpack.di.component.AppComponent
import nlab.practice.jetpack.di.component.ViewModelInjectComponent

/**
 * 의존성 주입을 지원하는 AndroidViewModel
 *
 * @author Doohyun
 * @since 2018. 11. 23
 */
abstract class InjectableAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val injector: ViewModelInjectComponent by lazy {
        if (application is AppComponent.Supplier) {
            (application as AppComponent.Supplier)
                    .getAppComponent()
                    .viewModelComponent()
                    .build()
        } else {
            throw Throwable("Error by none support AppComponent.Supplier")
        }
    }

}