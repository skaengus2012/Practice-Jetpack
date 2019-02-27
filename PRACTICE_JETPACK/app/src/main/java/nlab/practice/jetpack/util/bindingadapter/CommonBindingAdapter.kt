package nlab.practice.jetpack.util.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * 기본 동작 바인딩 어댑터 정의
 *
 * @author Doohyun
 * @since 2019. 01. 24
 */

@BindingAdapter("onTouch")
fun bindOnTouchListener(view: View, onTouchListener: View.OnTouchListener) {
    view.setOnTouchListener(onTouchListener)
}
