package nlab.practice.jetpack.util.di.module

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.repository.SimpleRepository
import nlab.practice.jetpack.util.di.scope.ViewModelLifeCycle

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
@Module
class RepositoryModule {

    @ViewModelLifeCycle
    @Provides
    fun provideSimpleRepository() : SimpleRepository = SimpleRepository()
}