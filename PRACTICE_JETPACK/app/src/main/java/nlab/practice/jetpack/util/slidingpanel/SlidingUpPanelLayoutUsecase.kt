package nlab.practice.jetpack.util.slidingpanel

import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import nlab.practice.jetpack.util.lazyPublic
import kotlin.math.max
import kotlin.math.min

/**
 * @author Doohyun
 * @since 2019. 05. 24
 */
class SlidingUpPanelLayoutUsecase(viewSupplier: () -> SlidingUpPanelLayout) {

    private val _slidingUpPanelLayout: SlidingUpPanelLayout by lazyPublic(viewSupplier)

    val slideOffsetSubject: Subject<Float> = PublishSubject.create()

    val slidePanelStateSubject: Subject<SlidingUpPanelLayout.PanelState> = PublishSubject.create()

    val currentPanelState: SlidingUpPanelLayout.PanelState
        get() = _slidingUpPanelLayout.panelState

    fun initialize() {
        _slidingUpPanelLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                val offset = min(max(0.0f, slideOffset), 1.0f)

                slideOffsetSubject.onNext(offset)
            }

            override fun onPanelStateChanged(
                    panel: View?,
                    previousState: SlidingUpPanelLayout.PanelState?,
                    newState: SlidingUpPanelLayout.PanelState?) {
                newState?.run {  slidePanelStateSubject.onNext(this) }
            }
        })
    }

    fun expand() {
        _slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
    }

    fun collapse() {
        _slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }
}