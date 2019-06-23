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

package nlab.practice.jetpack.util.recyclerview.touch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
@AutoFactory
class ItemViewTouchHelperUsecase(
        @Provided private val viewHolder: RecyclerView.ViewHolder,
        private val itemViewTouchHelper: ItemTouchHelper) {

    fun startDrag() {
        itemViewTouchHelper.startDrag(viewHolder)
    }
}