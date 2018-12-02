package nlab.practice.jetpack.util.di.component

import dagger.Subcomponent
import nlab.practice.jetpack.anko.AnkoViewModel
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import nlab.practice.jetpack.ui.viewmodel.MainTestViewModel
import nlab.practice.jetpack.util.di.module.ViewModelModule
import nlab.practice.jetpack.util.di.scope.ViewModelLifeCycle

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

    fun inject(mainTestViewModel: MainTestViewModel)

    fun inject(ankoViewModel: AnkoViewModel)

    fun inject(diTestViewModel: DITestViewModel)
}