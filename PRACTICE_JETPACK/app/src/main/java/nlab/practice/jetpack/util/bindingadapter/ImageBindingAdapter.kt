package nlab.practice.jetpack.util.bindingadapter

import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import nlab.practice.jetpack.util.GlideApp
import org.jetbrains.anko.imageResource

/**
 * 이미지 관련 항목 바인딩 어댑터 정의
 *
 * @author Doohyun
 * @since 2019. 01. 23
 */

@BindingAdapter(value = ["imageUrl", "glideOptions", "errorDrawable", "placeHolder"], requireAll = false)
fun setImageUrl(
        view: ImageView,
        imageUrl: String?,
        options: RequestOptions?,
        @DrawableRes errorDrawableRes: Int,
        @DrawableRes placeholderRes: Int) {

    val glideRequest = GlideApp.with(view.context)
            .load(imageUrl)
            .apply(options?: RequestOptions())

    errorDrawableRes.takeIf { it != 0 }?.run { glideRequest.error(this) }
    placeholderRes.takeIf { it != 0 }?.run { glideRequest.placeholder(this) }

    glideRequest.into(view)
}

@BindingAdapter("imgRes")
fun setImageResource(view: ImageView, @DrawableRes resource: Int) {
    if (resource == 0) {
        view.imageResource = android.R.color.transparent
    } else {
        view.imageResource = resource
    }
}