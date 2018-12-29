package nlab.practice.jetpack.common.di.fragment

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * @author Doohyun
 */
@Module
class FragmentCommonModule {

    @FragmentScope
    @Provides
    fun provideCompositeDisposables(): CompositeDisposable {
        val result : CompositeDisposable by lazy { CompositeDisposable() }

        return result
    }
}