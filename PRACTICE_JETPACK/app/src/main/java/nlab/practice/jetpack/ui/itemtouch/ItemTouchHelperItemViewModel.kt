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

package nlab.practice.jetpack.ui.itemtouch

import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
@AutoFactory
class ItemTouchHelperItemViewModel constructor(
    @Provided private val dragItemTouchHelper: ItemTouchHelper,
    private val pagingItem: PagingItem
) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_dragging_handle_item

    fun getTitle(): String = pagingItem.title

    fun getImageUrl(): String? = pagingItem.imageUrl

    fun startDrag(action: Int): Boolean = if (action == MotionEvent.ACTION_DOWN) {
        itemViewUsecaseFactory
            ?.itemViewTouchHelperUsecaseFactory()
            ?.create(dragItemTouchHelper)
            ?.startDrag()

        true
    } else {
        false
    }
}