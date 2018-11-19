package nlab.practice.jetpack.util.di.component

import dagger.Component
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import nlab.practice.jetpack.ui.viewmodel.MainTestViewModel
import nlab.practice.jetpack.util.di.module.RepositoryModule
import nlab.practice.jetpack.util.di.module.ViewModelModule
import nlab.practice.jetpack.util.di.scope.ViewModelLifeCycle

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@ViewModelLifeCycle
@Component(modules = [
    RepositoryModule::class, ViewModelModule::class
])
interface ViewModelComponent {

    fun inject(mainTestViewModel: MainTestViewModel)

    fun inject(diTestViewModel: DITestViewModel)
}