package nlab.practice.jetpack.ui.slide

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.isCollapsed
import nlab.practice.jetpack.util.slidingpanel.isExpanded
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingHolderViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val slideHolderViewUsecase: SlidingHolderViewUsecase,
        private val slidingHolderTransitionUsecase: SlidingHolderTransitionUsecase,
        private val disposables: CompositeDisposable) {

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            subscribeSlideOffset()
            subscribeSlideState()

            slidingHolderTransitionUsecase.replaceMainFragment()
        }
    }

    private fun subscribeSlideOffset() {
        slidingUpPanelLayoutUsecase?.slideOffsetSubject
                ?.doOnNext {
                    offset
                    ->
                    slideHolderViewUsecase.setContainerAlphaValue(Math.min((offset * 2), 1.0f))
                    slideHolderViewUsecase.setMiniPlayerAlphaValue(Math.max(1 - (offset * 7), 0.0f))
                }
                ?.subscribe()
                ?.addTo(disposables)
    }

    private fun subscribeSlideState() {
        slidingUpPanelLayoutUsecase?.slidePanelStateSubject
                ?.subscribe {
                    when {
                        it.isExpanded() ->  slideHolderViewUsecase.bringToFrontContainer()
                        it.isCollapsed() ->  slideHolderViewUsecase.bringToFrontMiniPlayer()
                        else -> {}
                    }
                }
                ?.addTo(disposables)
    }
}