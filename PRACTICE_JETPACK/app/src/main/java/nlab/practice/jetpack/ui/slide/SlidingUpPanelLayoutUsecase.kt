package nlab.practice.jetpack.ui.slide

import com.sothree.slidinguppanel.SlidingUpPanelLayout
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder

/**
 * @author Doohyun
 * @since 2019. 05. 24
 */
class SlidingUpPanelLayoutUsecase(viewSupplier: () -> SlidingUpPanelLayout) {

    private val _slidingUpPanelLayout: SlidingUpPanelLayout by lazy(viewSupplier)

}