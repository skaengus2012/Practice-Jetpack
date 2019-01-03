package nlab.practice.jetpack.util.di.itemview

import android.view.View
import dagger.BindsInstance
import dagger.Component
import nlab.practice.jetpack.util.NavigateViewUsecase

/**
 * @author Doohyun
 */
@ItemViewScope
@Component
interface ItemViewUsecaseFactory {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setView(view: View): Builder

        fun build(): ItemViewUsecaseFactory
    }

    fun testItemViewUsecase(): TestItemViewUsecase

    fun navigateViewUsecase(): NavigateViewUsecase
}