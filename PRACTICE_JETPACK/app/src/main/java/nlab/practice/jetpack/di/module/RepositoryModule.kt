package nlab.practice.jetpack.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.repository.SimpleRepository
import nlab.practice.jetpack.repository.TestMenuRepository
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

    @Singleton
    @Provides
    fun provideTestMenuRepository(context: Context): TestMenuRepository = TestMenuRepository(context)
}