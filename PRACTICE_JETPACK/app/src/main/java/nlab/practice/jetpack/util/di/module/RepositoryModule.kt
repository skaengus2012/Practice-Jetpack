package nlab.practice.jetpack.util.di.module

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.repository.SimpleRepository
import javax.inject.Singleton

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSimpleRepository() : SimpleRepository = SimpleRepository()
}