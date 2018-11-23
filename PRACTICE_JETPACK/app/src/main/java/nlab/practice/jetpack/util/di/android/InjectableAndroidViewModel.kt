package nlab.practice.jetpack.util.di.android

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import nlab.practice.jetpack.util.di.component.DaggerViewModelInjectComponent
import nlab.practice.jetpack.util.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.util.di.module.ViewModelModule

/**
 * 의존성 주입을 지원하는 AndroidViewModel
 *
 * @author Doohyun
 * @since 2018. 11. 23
 */
abstract class InjectableAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val injector: ViewModelInjectComponent by lazy {
        DaggerViewModelInjectComponent.builder().viewModelModule(ViewModelModule(application)).build()
    }

}