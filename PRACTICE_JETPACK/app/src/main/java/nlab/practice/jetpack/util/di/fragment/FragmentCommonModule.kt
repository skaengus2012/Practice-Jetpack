package nlab.practice.jetpack.util.di.fragment

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.FragmentCallback
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.component.lifecycle.LifeCycleBinder
import nlab.practice.jetpack.util.nav.*
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import javax.inject.Named


/**
 * Fragment 공통 모듈 정의
 *
 * @author Doohyun
 */
@Module
class FragmentCommonModule {

    @FragmentScope
    @Provides
    fun provideDisposables() = CompositeDisposable()

    @FragmentScope
    @Provides
    fun provideFragmentCallback() = FragmentCallback()

    @Named(ContextInjectionType.ACTIVITY)
    @FragmentScope
    @Provides
    fun provideActivityNavUsecase(fragment: Fragment): ActivityNavUsecase {
        return DefaultActivityNavUsecase(fragment.activity!!)
    }

    @FragmentScope
    @Provides
    fun provideActivityCommonUsecase(fragment: Fragment) = ActivityCommonUsecase(fragment.activity!!)

    @Named(ContextInjectionType.ACTIVITY)
    @FragmentScope
    @Provides
    fun provideIntentProvider(fragment: Fragment) = IntentProvider(fragment.activity!!)

    @Named(ContextInjectionType.FRAGMENT)
    @FragmentScope
    @Provides
    fun provideFragmentOwnerActivityNavUsecase(fragment: Fragment): ActivityNavUsecase {
        return FragmentOwnerActivityNavUsecase(fragment)
    }

    @FragmentScope
    @Provides
    fun provideLifeCycleBinder(disposables: CompositeDisposable): FragmentLifeCycleBinder = LifeCycleBinder(disposables)

    @FragmentScope
    @Provides
    fun provideLayoutManagerFactory(fragment: Fragment) = LayoutManagerFactory(fragment.activity!!)

    @FragmentScope
    @Provides
    fun provideSnackBarHelper(
            fragment: Fragment,
            resourceProvider: ResourceProvider) = SnackBarHelper(resourceProvider) {
        fragment.activity
                ?.takeUnless { it.isFinishing }
                ?.run { findViewById(android.R.id.content) }
    }
}