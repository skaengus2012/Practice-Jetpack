package nlab.practice.jetpack.di.component

import dagger.Subcomponent
import nlab.practice.jetpack.ui.ankomvvm.AnkoFirstViewModel
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import nlab.practice.jetpack.ui.viewmodel.MainTestViewModel
import nlab.practice.jetpack.di.module.ViewModelModule
import nlab.practice.jetpack.di.scope.ViewModelLifeCycle
import nlab.practice.jetpack.ui.home.HomeHeaderViewModel

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@ViewModelLifeCycle
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelInjectComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : ViewModelInjectComponent
    }

    fun inject(viewModel: AnkoFirstViewModel)
    fun inject(viewModel: HomeHeaderViewModel)
    fun inject(viewModel: MainTestViewModel)
    fun inject(viewModel: DITestViewModel)
}