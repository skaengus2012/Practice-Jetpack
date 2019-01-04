package nlab.practice.jetpack.repository

import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.util.ResourceProvider

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun provideTestMenuRepository(resourceProvider: ResourceProvider): TestMenuRepository = TestMenuRepository(resourceProvider)
}