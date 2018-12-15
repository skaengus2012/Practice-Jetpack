package nlab.practice.jetpack.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.di.scope.ViewModelLifeCycle
import nlab.practice.jetpack.ui.home.HomeItemDecoration

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
@Module
class ItemDecorationModule {

    @ViewModelLifeCycle
    @Provides
    fun provideHomeDecoration(context: Context) = HomeItemDecoration(context)
}