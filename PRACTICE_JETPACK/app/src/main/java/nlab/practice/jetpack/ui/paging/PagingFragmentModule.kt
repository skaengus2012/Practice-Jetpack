package nlab.practice.jetpack.ui.paging

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.PagingDataSourceRepository
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.di.fragment.FragmentScope

/**
 * @author Doohyun
 */
@Module(includes = [
    ChildFragmentModule::class
])
@Deprecated("실험작으로 만든 모듈 추후 제거 대상")
class PagingFragmentModule {

    @FragmentScope
    @Provides
    fun providePagingItemViewModelFactory() : PagingItemViewModelFactory = PagingItemViewModelFactory()

    @FragmentScope
    @Provides
    fun providePagingDataSourceFactory(
            pagingDataSourceRepository: PagingDataSourceRepository,
            disposables: CompositeDisposable) : PagingItemPositionalDataSourceFactory {
        return PagingItemPositionalDataSourceFactory({pagingDataSourceRepository}, {disposables})
    }
}