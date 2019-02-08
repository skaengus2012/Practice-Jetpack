package nlab.practice.jetpack.ui.listadapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * ListAdapterExample 에서 사용하는 BindingAdapter 열람
 *
 * @author Doohyun
 */
@BindingAdapter("normalToolbarStateOfListAdapterExample")
fun bindListAdapterExampleNormalToolbarState(view: View, isSelectMode: Boolean) {
    if (isSelectMode) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("selectToolbarStateOfListAdapterExample")
fun bindListAdapterExampleSelectToolbarState(view: View, isSelectMode: Boolean) {
    if (isSelectMode) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}