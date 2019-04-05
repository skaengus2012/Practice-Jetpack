package nlab.practice.jetpack.ui.home

import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import nlab.practice.jetpack.util.ResourceProvider

/**
 * @author Doohyun
 */

@BindingAdapter("home_card_color")
fun setHomeCardColor(cardView: CardView, @ColorRes colorRes: Int) {
    cardView.setCardBackgroundColor(ResourceProvider(cardView.context).getColor(colorRes))
}