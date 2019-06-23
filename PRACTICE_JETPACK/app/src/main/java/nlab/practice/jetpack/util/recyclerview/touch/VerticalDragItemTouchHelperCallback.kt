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
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
class VerticalDragItemTouchHelperCallback(private val isLongPressDragEnabled: Boolean = true) : ItemTouchHelper.Callback() {
    val eventSubject: PublishSubject<DragEvent> = PublishSubject.create()

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean {

        eventSubject.onNext(DragEvent(viewHolder.adapterPosition, target.adapterPosition))
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFrags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        return ItemTouchHelper.Callback.makeMovementFlags(dragFrags, 0)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun isLongPressDragEnabled(): Boolean = isLongPressDragEnabled
}