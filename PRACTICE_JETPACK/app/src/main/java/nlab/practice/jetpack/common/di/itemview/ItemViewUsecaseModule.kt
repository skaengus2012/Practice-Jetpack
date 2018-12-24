package nlab.practice.jetpack.common.di.itemview

import android.view.View
import dagger.Module
import dagger.Provides

/**
 * @author Doohyun
 */
@Module
class ItemViewUsecaseModule(private val view: View) {

    @Provides
    fun provideView(): View = view
}