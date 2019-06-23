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

import android.animation.Animator
import android.view.View
import androidx.databinding.BindingAdapter
import nlab.practice.jetpack.util.SimpleAnimatorListener

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

@BindingAdapter("animationVisibility")
fun setAnimationVisibility(view: View, targetVisibility: Int) {
    val isEqualsVisibility = (view.visibility == targetVisibility)
    if (!isEqualsVisibility) {
        val animateListener = object: SimpleAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = targetVisibility
            }
        }

        if (targetVisibility == View.VISIBLE) {
            view.animate().alpha(1.0f)
        } else {
            view.animate().alpha(0.0f)
        }.setListener(animateListener).start()
    }
}