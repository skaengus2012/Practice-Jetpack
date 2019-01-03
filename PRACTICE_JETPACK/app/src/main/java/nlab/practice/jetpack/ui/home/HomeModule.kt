package nlab.practice.jetpack.ui.home

import dagger.Module
import dagger.Provides

/**
 * Home 에서 제공해줘야 하는 Module
 *
 * @author Doohyun
 */
@Module
class HomeModule {

    @Provides
    fun homeItemFactory(): HomeItemViewModelFactory = HomeItemViewModelFactory()
}