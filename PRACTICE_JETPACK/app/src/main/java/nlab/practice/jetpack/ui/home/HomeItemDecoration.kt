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

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Inject

/**
 * Home 의 전체 데코레이션 정의
 *
 * @author Doohyun
 */
class HomeItemDecoration @Inject constructor(
        private val resourceProvider: ResourceProvider): RecyclerView.ItemDecoration()  {

    private val bottomMargin : Int by lazy { resourceProvider.getDimensionPixelSize(R.dimen.home_page_item_margin_bottom) }
    private val sizeMargin : Int by lazy { resourceProvider.getDimensionPixelSize(R.dimen.home_page_item_margin_horizontal) }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.bottom = bottomMargin
        }

        outRect.left = sizeMargin
        outRect.right = sizeMargin
    }
}