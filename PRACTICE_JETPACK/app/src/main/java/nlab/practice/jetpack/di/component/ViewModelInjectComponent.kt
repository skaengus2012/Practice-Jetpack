package nlab.practice.jetpack.di.component

import dagger.Subcomponent
import nlab.practice.jetpack.util.di.itemview.ItemDecorationModule
import nlab.practice.jetpack.ui.tutorial.AnkoFirstViewModel
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import nlab.practice.jetpack.ui.viewmodel.MainTestViewModel
import nlab.practice.jetpack.di.module.ViewModelModule
import nlab.practice.jetpack.di.scope.ViewModelLifeCycle
import nlab.practice.jetpack.ui.home.HomeHeaderViewModel
import nlab.practice.jetpack.ui.home.HomeItemViewModel
import nlab.practice.jetpack.ui.home.HomeDeViewModel

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@ViewModelLifeCycle
@Subcomponent(modules = [ViewModelModule::class, ItemDecorationModule::class])
interface ViewModelInjectComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build() : ViewModelInjectComponent
    }

    fun inject(viewModel: AnkoFirstViewModel)
    fun inject(viewModel: HomeDeViewModel)
    fun inject(viewModel: HomeHeaderViewModel)
    fun inject(viewModel: HomeItemViewModel)
    fun inject(viewModel: MainTestViewModel)
    fun inject(viewModel: DITestViewModel)
}