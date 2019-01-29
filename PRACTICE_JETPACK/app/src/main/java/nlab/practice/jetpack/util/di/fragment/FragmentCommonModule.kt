package nlab.practice.jetpack.util.di.fragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.RxUtils
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.nav.ActivityNavUsecase
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.component.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.nav.FragmentScopeNavModule


/**
 * Fragment 공통 모듈 정의
 *
 * @author Doohyun
 */
@Module(includes = [
    FragmentScopeNavModule::class
])
class FragmentCommonModule {

    @FragmentScope
    @Provides
    fun provideDisposables(): CompositeDisposable = RxUtils.createLazyDisposables()

    @FragmentScope
    @Provides
    fun provideActivityNavUsecase(fragment: Fragment): ActivityNavUsecase = ActivityNavUsecase(fragment.activity!!)

    @FragmentScope
    @Provides
    fun provideActivityCommonUsecase(fragment: Fragment): ActivityCommonUsecase = ActivityCommonUsecase(fragment.activity!!)

    @FragmentScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): FragmentLifeCycleBinder = LifeCycleBinder(disposables)
}