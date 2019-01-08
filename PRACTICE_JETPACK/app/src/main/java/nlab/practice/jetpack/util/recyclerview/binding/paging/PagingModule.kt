package nlab.practice.jetpack.util.recyclerview.binding.paging

import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author Doohyun
 * @since 2019. 01. 10
 */
@Module
class PagingModule {

    @Reusable
    @Provides
    fun providePagingItemCallbackFactory(): PagingItemCallbackFactory = PagingItemCallbackFactory()
}