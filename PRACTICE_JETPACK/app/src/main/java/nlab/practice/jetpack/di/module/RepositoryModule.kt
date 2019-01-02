package nlab.practice.jetpack.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import nlab.practice.jetpack.repository.SimpleRepository
import nlab.practice.jetpack.repository.TestMenuRepository

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @Reusable
    @Provides
    fun provideSimpleRepository() : SimpleRepository = SimpleRepository()

    @Reusable
    @Provides
    fun provideTestMenuRepository(context: Context): TestMenuRepository = TestMenuRepository(context)
}