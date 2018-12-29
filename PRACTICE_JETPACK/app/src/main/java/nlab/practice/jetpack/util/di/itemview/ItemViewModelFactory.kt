package nlab.practice.jetpack.util.di.itemview

import dagger.Subcomponent

/**
 * ItemViewModel 생산을 위한
 *
 * @author Doohyun
 */
@Subcomponent(modules = [
    ItemViewModelFactoryModule::class
])
interface ItemViewModelFactory {

    @Subcomponent.Builder
    interface Builder {
        fun build() : ItemViewModelFactory
    }
}