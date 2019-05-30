package nlab.practice.jetpack.ui.slide

import com.sothree.slidinguppanel.SlidingUpPanelLayout
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 * @since 2019. 05. 24
 */
class SlidingUpPanelLayoutUsecase(viewSupplier: () -> SlidingUpPanelLayout) {

    private val _slidingUpPanelLayout: SlidingUpPanelLayout by lazyPublic(viewSupplier)

}