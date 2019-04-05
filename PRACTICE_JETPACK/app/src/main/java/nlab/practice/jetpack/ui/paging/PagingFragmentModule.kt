package nlab.practice.jetpack.ui.paging

import androidx.fragment.app.Fragment
import androidx.paging.DataSource
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_paging.*
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule

internal typealias DFactory = DataSource.Factory<Int, PagingItemPracticeViewModel>

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
@Module(includes = [
    ChildFragmentModule::class,
    PositionalPagingModule::class
])
class PagingFragmentModule {

    @Provides
    fun providePagingItemViewModelFactory(fragmentNavUsecase: FragmentNavUsecase): PagingItemPracticeViewModelFactory {
        return PagingItemPracticeViewModelFactory{fragmentNavUsecase}
    }

    @Provides
    fun provideRecyclerViewUsecase(fragment: Fragment): RecyclerViewUsecase = RecyclerViewUsecase {
        fragment.lvContents
    }
}