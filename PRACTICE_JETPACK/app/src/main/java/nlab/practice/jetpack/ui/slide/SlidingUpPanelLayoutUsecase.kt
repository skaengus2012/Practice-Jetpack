package nlab.practice.jetpack.ui.slide

import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 * @since 2019. 05. 24
 */
class SlidingUpPanelLayoutUsecase(viewSupplier: () -> SlidingUpPanelLayout) {

    private val _slidingUpPanelLayout: SlidingUpPanelLayout by lazyPublic(viewSupplier)

    val slideOffsetSubject: Subject<Float> = PublishSubject.create()

    val panelState: SlidingUpPanelLayout.PanelState?
        get() = _slidingUpPanelLayout.panelState

    fun initialize() {
        _slidingUpPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                slideOffsetSubject.onNext(slideOffset)
            }

            override fun onPanelStateChanged(
                    panel: View?,
                    previousState: SlidingUpPanelLayout.PanelState?,
                    newState: SlidingUpPanelLayout.PanelState?) {
                // TODO 필요에 따라 구현 필요.
            }
        })
    }
}