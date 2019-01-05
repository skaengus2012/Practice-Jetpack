package nlab.practice.jetpack.util.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.home.HomeModule
import nlab.practice.jetpack.ui.paging.PagingFragment
import nlab.practice.jetpack.ui.paging.PagingModule

/**
 * Fragment 를 Binding 하기 위한 모듈
 *
 * @author Doohyun
 */
@Module
abstract class FragmentBindModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        HomeModule::class
    ])
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        PagingModule::class
    ])
    abstract fun pagingFragment(): PagingFragment
}