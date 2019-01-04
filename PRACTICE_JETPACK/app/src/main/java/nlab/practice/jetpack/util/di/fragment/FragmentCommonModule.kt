package nlab.practice.jetpack.util.di.fragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.ActivityStarterUsecase
import nlab.practice.jetpack.util.createLazyCompositeDisposable
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.lifecycle.LifeCycleBinder
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
    fun provideDisposables(): CompositeDisposable = createLazyCompositeDisposable()

    @FragmentScope
    @Provides
    fun provideActivityStarterUsecase(fragment: Fragment): ActivityStarterUsecase = ActivityStarterUsecase(fragment.activity!!)

    @FragmentScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): FragmentLifeCycleBinder = LifeCycleBinder(disposables)
}