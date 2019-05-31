package nlab.practice.jetpack.ui.slide

import android.view.View
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 */
class SlidingHolderViewUsecase(containerViewSupplier: () -> View, private val _miniPlayerViewSupplier: () -> View?) {

    private val _containerView: View by lazyPublic(containerViewSupplier)

    private val _miniPlayerView: View?
        get() = _miniPlayerViewSupplier()

    fun setContainerAlphaValue(offset: Float) {
        _containerView.alpha = offset
    }

    fun setMiniPlayerAlphaValue(offset: Float) {
        _miniPlayerView?.run {
            alpha = offset
        }
    }

    fun bringToFrontContainer() {
        /**
         * NOTE : bringToFront z 축을 맨 위로 올리는 것을 의미
         */
        _containerView.bringToFront()
    }

    fun bringToFrontMiniPlayer() {
        _miniPlayerView?.bringToFront()
    }
}