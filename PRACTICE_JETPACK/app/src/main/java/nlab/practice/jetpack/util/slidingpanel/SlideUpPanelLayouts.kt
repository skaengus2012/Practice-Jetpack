package nlab.practice.jetpack.util.slidingpanel

import androidx.fragment.app.Fragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.ViewSupplier
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import javax.inject.Inject

private typealias SlidingUpPanelLayoutSupplier = ViewSupplier<SlidingUpPanelLayout>

/**
 * SlidingUpPanelLayout 을 사용하는 Activity 가 사용할 객체를 소유한 클래스
 */
class SlidingUpPanelActivity @Inject constructor(val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase){

    interface Owner {
        fun getSlidingUpPanelDelegate(): SlidingUpPanelActivity
    }
}

@Module
class SlidingUpPanelActivityModule {

    @ActivityScope
    @Provides
    fun provideSlidingUpPanelLayoutUsecase(viewSupplier: SlidingUpPanelLayoutSupplier): SlidingUpPanelLayoutUsecase {
        return SlidingUpPanelLayoutUsecase(viewSupplier::get)
    }
}

@Module
class SlidingUpPanelFragmentModule {

    @FragmentScope
    @Provides
    fun provideSlidingUpPanelLayoutUsecase(fragment: Fragment): SlidingUpPanelLayoutUsecase? {
        return fragment.activity
                ?.let { it as? SlidingUpPanelActivity.Owner }
                ?.getSlidingUpPanelDelegate()
                ?.slidingUpPanelLayoutUsecase
    }

}