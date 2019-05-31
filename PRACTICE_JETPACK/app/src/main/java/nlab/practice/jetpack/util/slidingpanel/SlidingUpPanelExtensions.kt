package nlab.practice.jetpack.util.slidingpanel

import com.sothree.slidinguppanel.SlidingUpPanelLayout


fun SlidingUpPanelLayout.PanelState.isExpanded(): Boolean {
    return this == SlidingUpPanelLayout.PanelState.EXPANDED
}

fun SlidingUpPanelLayout.PanelState.isCollapsed(): Boolean {
    return this == SlidingUpPanelLayout.PanelState.COLLAPSED
}