package nlab.practice.jetpack.ui.home

import android.view.View
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import nlab.practice.jetpack.util.ResourceProvider

/**
 * @author Doohyun
 */

@BindingAdapter("home_card_color")
fun setHomeCardColor(view: View, @ColorRes colorRes: Int) {
    view.setBackgroundColor(ResourceProvider(view.context).getColor(colorRes))
}