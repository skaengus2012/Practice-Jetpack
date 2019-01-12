package nlab.practice.jetpack.util.recyclerview.paging

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager

/**
 * Paging 관련 컴포넌트 제공자
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
@Module
class PagingModule {

    @Provides
    fun provideFixSizePositionalPagingManagerFactory(disposables: CompositeDisposable) : CountablePositionalPagingManager.Factory {
        return CountablePositionalPagingManager.Factory(disposables)
    }
}