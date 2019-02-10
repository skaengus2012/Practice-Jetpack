package nlab.practice.jetpack.ui.listadapter

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_list_adapter_example.view.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.AppBarStateChangeListener
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SimpleAnimationListener



/**
 * ListAdapterExample 에서 사용하는 BindingAdapter 열람
 *
 * @author Doohyun
 */
@BindingAdapter("selectModeOfListAdapterExample")
fun bindListAdapterExampleSelectMode(view: View, isSelectMode: Boolean) {
    val toolbarStandard = view.toolbarStandard

    val currentStandardToolbarVisibility = toolbarStandard.visibility
    val targetStandardToolbarVisibility = if (isSelectMode) {
        View.GONE
    } else {
        View.VISIBLE
    }

    val isDifferState = (currentStandardToolbarVisibility != targetStandardToolbarVisibility)
    if (isDifferState) {
        val toolbarSelect = view.toolbarSelect
        val swipeRefreshLayout = view.swipeContainer
        val collapsingToolbar = view.collapsingToolbar

        val isNeedShowStandardView = targetStandardToolbarVisibility == View.VISIBLE

        if (isNeedShowStandardView) {
            swipeRefreshLayout.isEnabled = true

            setCollapsingEnterAlwaysMode(collapsingToolbar)
            convertStandardToolbarTransition(toolbarStandard, toolbarSelect)

        } else {
            val appbarLayout = view.appbarLayout

            // 선택모드에선 리프레쉬 안되게 고정
            swipeRefreshLayout.isEnabled = false

            // Toolbar 가 완전히 내려온 상태가 아니라면, 내린다음 트랜지션을 수행
            // 리스너는 등록하고 바로 해제하는 용도로 사용
            val instantAppBarListener = object: AppBarStateChangeListener() {
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: Int) {
                    if (state == State.EXPANDED) {
                        setCollapsingDisableMode(collapsingToolbar)
                        convertSelectToolbarTransition(toolbarStandard, toolbarSelect)

                        appbarLayout.removeOnOffsetChangedListener(this)
                    }
                }
            }

            appbarLayout.addOnOffsetChangedListener(instantAppBarListener)
            appbarLayout.setExpanded(true, true)
        }
    }
}

private fun setCollapsingEnterAlwaysMode(collapsingLayout: CollapsingToolbarLayout) {
    getAppBarLayoutParams(collapsingLayout)?.run {
        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        collapsingLayout.layoutParams = this
    }
}

private fun getAppBarLayoutParams(collapsingLayout: CollapsingToolbarLayout): AppBarLayout.LayoutParams? {
    return collapsingLayout.layoutParams as? AppBarLayout.LayoutParams
}

private fun convertStandardToolbarTransition(toolbarStandard: View, toolbarSelect: View) {
    toolbarSelect.visibility = View.GONE

    toolbarStandard.animate().setDuration(200)
            .withStartAction { toolbarStandard.visibility = View.VISIBLE }
            .translationY(0f)
            .withLayer()
}

private fun setCollapsingDisableMode(collapsingLayout: CollapsingToolbarLayout) {
    collapsingLayout.minimumHeight =
            ResourceProvider(collapsingLayout.context).getDimensionPixelSize(R.dimen.list_adapter_toolbar_height)

    getAppBarLayoutParams(collapsingLayout)?.run {
        scrollFlags = 0
        collapsingLayout.layoutParams = this
    }
}

private fun convertSelectToolbarTransition(toolbarStandard: View, toolbarSelect: View) {
    val viewHeight = toolbarStandard.height.toFloat()

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