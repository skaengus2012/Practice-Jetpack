/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.util.bindingadapter

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
    @DrawableRes placeholderRes: Int
) {

    val glideRequest = GlideApp.with(view.context)
        .load(imageUrl)
        .apply(options ?: RequestOptions())

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