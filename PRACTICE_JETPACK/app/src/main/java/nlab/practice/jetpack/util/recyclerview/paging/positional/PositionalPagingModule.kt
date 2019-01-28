package nlab.practice.jetpack.util.recyclerview.paging.positional

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Paging 관련 컴포넌트 제공자
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
@Module
class PositionalPagingModule {

    @Provides
    fun provideCountablePositionalPagingManagerFactory(disposables: CompositeDisposable): CountablePositionalPagingManager.Factory {
        return CountablePositionalPagingManager.Factory(disposables)
    }

    @Provides
    fun provideUnboundedPositionalPagingManagerFactory(disposables: CompositeDisposable): UnboundedPositionalPagingManager.Factory {
        return UnboundedPositionalPagingManager.Factory(disposables)
    }
}