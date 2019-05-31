package nlab.practice.jetpack.util.slidingpanel

import com.sothree.slidinguppanel.SlidingUpPanelLayout

/**
 * @author Doohyun
 */
object SlidingUpPanelUtils {

    fun isExpanded(state: SlidingUpPanelLayout.PanelState): Boolean {
        return state == SlidingUpPanelLayout.PanelState.EXPANDED
    }

}