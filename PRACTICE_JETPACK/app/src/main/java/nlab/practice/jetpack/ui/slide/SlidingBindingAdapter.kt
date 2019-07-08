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

package nlab.practice.jetpack.ui.slide

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import jp.wasabeef.glide.transformations.BlurTransformation
import nlab.practice.jetpack.util.GlideApp

/**
 * @author Doohyun
 */

private const val PREFIX = "sliding_"

object SlidingBindingAdapter {

    @JvmStatic
    @BindingAdapter("${PREFIX}imageUrl")
    fun setImage(view: ImageView, imgUrl: String?) {
        if (imgUrl == null) {
            view.setImageDrawable(null)
        } else {
            GlideApp.with(view.context)
                    .asBitmap()
                    .load(imgUrl)
                    .transform(BlurTransformation(3, 25))
                    .into(view)
        }

    }
}