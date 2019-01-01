package nlab.practice.jetpack.util.di.itemview

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.ui.home.HomeItemDecoration

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
@Module
class ItemDecorationModule {

    @Reusable
    @Provides
    fun provideHomeDecoration(context: Context) = HomeItemDecoration(context)
}