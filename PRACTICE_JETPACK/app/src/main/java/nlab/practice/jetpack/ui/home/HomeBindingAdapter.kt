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