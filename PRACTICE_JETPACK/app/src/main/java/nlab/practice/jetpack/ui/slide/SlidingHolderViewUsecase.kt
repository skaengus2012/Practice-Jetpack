package nlab.practice.jetpack.ui.slide

import android.view.View
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 */
class SlidingHolderViewUsecase(containerViewSupplier: () -> View, miniPlayerViewSupplier: () -> View?) {

    private val containerView: View by lazyPublic(containerViewSupplier)

    private val miniPlayerView: View? by lazyPublic(miniPlayerViewSupplier)

    fun setContainerAlphaValue(offset: Float) {
        containerView.alpha = offset
    }

    fun setMiniPlayerAlphaValue(offset: Float) {
        miniPlayerView?.run {
            alpha = offset
        }
    }

    fun bringToFrontContainer() {
        /**
         * NOTE : bringToFront z 축을 맨 위로 올리는 것을 의미
         */
        containerView.bringToFront()
    }

    fun bringToFrontMiniPlayer() {
        miniPlayerView?.bringToFront()
    }
}