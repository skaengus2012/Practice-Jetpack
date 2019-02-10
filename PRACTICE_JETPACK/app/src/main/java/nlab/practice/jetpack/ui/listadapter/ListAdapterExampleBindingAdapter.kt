package nlab.practice.jetpack.ui.listadapter

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.databinding.BindingAdapter
import kotlinx.android.synthetic.main.fragment_list_adapter_example.view.*
import nlab.practice.jetpack.util.SimpleAnimationListener

/**
 * ListAdapterExample 에서 사용하는 BindingAdapter 열람
 *
 * @author Doohyun
 */
@BindingAdapter("toolbarStateOfListAdapterExample")
fun bindListAdapterExampleToolbarState(view: View, isSelectMode: Boolean) {
    val toolbarStandard = view.toolbarStandard
    val toolbarSelect = view.toolbarSelect

    val currentStandardToolbarVisibility = toolbarStandard.visibility
    val targetStandardToolbarVisibility = if (isSelectMode) {
        View.GONE
    } else {
        View.VISIBLE
    }

    val isDifferState = (currentStandardToolbarVisibility != targetStandardToolbarVisibility)
    if (isDifferState) {
        val viewHeight = toolbarStandard.height.toFloat()
        val isNeedShowStandardView = targetStandardToolbarVisibility == View.VISIBLE

        if (isNeedShowStandardView) {
            toolbarSelect.visibility = View.GONE

            toolbarStandard.animate().setDuration(200)
                    .withStartAction {
                        toolbarStandard.visibility = View.VISIBLE
                    }
                    .translationY(0f)
                    .withLayer()

        } else {
            toolbarStandard.animate().setDuration(100)
                    .withEndAction {
                        toolbarStandard.visibility =  View.GONE

                        AlphaAnimation(0f, 1f).apply {
                            duration = 300

                            setAnimationListener(object: SimpleAnimationListener() {
                                override fun onAnimationStart(animation: Animation?) {
                                    toolbarSelect.visibility = View.VISIBLE
                                }
                            })
                        }.run {  toolbarSelect.startAnimation(this) }
                    }
                    .translationY(-viewHeight)
                    .withLayer()
        }
    }
}