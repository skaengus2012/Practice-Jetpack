package nlab.practice.jetpack.util.di.itemview

import dagger.Component

/**
 * @author Doohyun
 */
@ItemViewScope
@Component(modules = [
    ItemViewUsecaseModule::class
])
interface ItemViewUsecaseComponent {

    fun testItemViewUsecase(): TestItemViewUsecase
}