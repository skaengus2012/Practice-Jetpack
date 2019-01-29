package nlab.practice.jetpack.util.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.home.HomeModule
import nlab.practice.jetpack.ui.paging.CountablePagingFragment
import nlab.practice.jetpack.ui.paging.UnboundedPagingFragment

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
        CountablePagingFragment.Module::class
    ])
    abstract fun countablePagingFragment(): CountablePagingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [
        FragmentCommonModule::class,
        UnboundedPagingFragment.Module::class
    ])
    abstract fun unboundedPagingFragment(): UnboundedPagingFragment
}