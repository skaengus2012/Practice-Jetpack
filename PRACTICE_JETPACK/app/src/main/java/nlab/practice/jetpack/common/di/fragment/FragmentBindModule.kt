package nlab.practice.jetpack.common.di.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nlab.practice.jetpack.ui.home.HomeFragment

/**
 * Fragment 를 Binding 하기 위한 모듈
 *
 * @author Doohyun
 */
@Module
abstract class FragmentBindModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentCommonModule::class])
    abstract fun homeFragment(): HomeFragment
}