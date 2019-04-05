package nlab.practice.jetpack.util

import android.animation.Animator
import android.view.animation.Animation

/**
 * @author Doohyun
 */
open class SimpleAnimationListener : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {}

    override fun onAnimationStart(animation: Animation?) {}
}

open class SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationStart(animation: Animator?) {}
}