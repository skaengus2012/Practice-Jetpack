package nlab.practice.jetpack.util

import androidx.annotation.IntDef
import com.google.android.material.appbar.AppBarLayout

/**
 * @author Doohyun
 */
open class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    @IntDef(State.IDLE, State.EXPANDED, State.COLLAPSED)
    annotation class State {
        companion object {
            const val EXPANDED = 0
            const val COLLAPSED = 1
            const val IDLE = 2
        }
    }

    var state = State.IDLE

    override fun onOffsetChanged(appBar: AppBarLayout?, offset: Int) {
        when {
            offset == 0 -> {
                if (state != State.EXPANDED) {
                    onStateChanged(appBar, State.EXPANDED)
                }

                state = State.EXPANDED
            }

            Math.abs(offset) >= appBar?.totalScrollRange ?: 0 -> {
                if (state != State.COLLAPSED) {
                    onStateChanged(appBar, State.COLLAPSED)
                }

                state = State.COLLAPSED
            }

            else -> {
                if (state != State.IDLE) {
                    onStateChanged(appBar, State.IDLE)
                }

                state = State.IDLE
            }
        }
    }

    open fun onStateChanged(appBarLayout: AppBarLayout?, @State state: Int) {}
}