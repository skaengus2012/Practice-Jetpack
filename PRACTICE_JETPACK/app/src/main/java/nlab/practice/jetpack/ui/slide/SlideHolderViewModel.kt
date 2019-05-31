package nlab.practice.jetpack.ui.slide

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelUtils
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideHolderViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val _slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val _slideHolderViewUsecase: SlideHolderViewUsecase,
        private val _disposables: CompositeDisposable) {

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            subscribeSlideOffset()
            subscribeSlideState()
        }
    }

    private fun subscribeSlideOffset() {
        _slidingUpPanelLayoutUsecase?.slideOffsetSubject
                ?.doOnNext {
                    offset
                    ->
                    _slideHolderViewUsecase.setContainerAlphaValue(Math.min((offset * 2), 1.0f))
                    _slideHolderViewUsecase.setMiniPlayerAlphaValue(Math.max(1 - (offset * 7), 0.0f))
                }
                ?.subscribe()
                ?.addTo(_disposables)
    }

    private fun subscribeSlideState() {
        _slidingUpPanelLayoutUsecase?.slidePanelStateSubject
                ?.subscribe {
                    val isExpanded = SlidingUpPanelUtils.isExpanded(it)
                    if (isExpanded) {
                        _slideHolderViewUsecase.bringToFrontContainer()
                    } else {
                        _slideHolderViewUsecase.bringToFrontMiniPlayer()
                    }
                }
                ?.addTo(_disposables)
    }
}