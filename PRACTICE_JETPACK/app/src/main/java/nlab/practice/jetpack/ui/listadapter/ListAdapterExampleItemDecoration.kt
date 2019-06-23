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

package nlab.practice.jetpack.ui.listadapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Inject

/**
 * @author Doohyun
 */
class ListAdapterExampleItemDecoration @Inject constructor(
        resourceProvider: ResourceProvider): RecyclerView.ItemDecoration() {

    private val horizontalInnerSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal_inner)
    private val horizontalSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal)
    private val bottomSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_bottom)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val column = position % ListAdapterExampleViewModel.SPAN_COUNT

        if (column == 0) {
            outRect.left = horizontalSize
            outRect.right = horizontalInnerSize
        } else {
            outRect.left = horizontalInnerSize
            outRect.right = horizontalSize
        }

        outRect.bottom = bottomSize
    }
}