package nlab.practice.jetpack.repository

import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Singleton

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun provideTestMenuRepository(resourceProvider: ResourceProvider) = TestMenuRepository(resourceProvider)

    @Reusable
    @Provides
    fun provideImagePoolRepository() = ImagePoolRepository()

    @Singleton
    @Provides
    fun providePagingDataSourceRepository(imagePoolRepository: ImagePoolRepository) = PagingItemRepository(imagePoolRepository)

    @Reusable
    @Provides
    fun provideHistoryRepository() = HistoryRepository()

    @Reusable
    @Provides
    fun provideCoverRepository() = CoverRepository()

    @Singleton
    @Provides
    fun providePlayerRepository(imagePoolRepository: ImagePoolRepository) = PlayerRepository(imagePoolRepository)

    @Reusable
    @Provides
    fun provideLyricsRepository() = LyricsRepository()
}