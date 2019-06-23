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

package nlab.practice.jetpack.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * If recyclerView has LinearLayoutManager (or Extends class),
 * it will be return 'LinearLayoutManager.findFirstVisibleItemPosition()'
 *
 * if it's not, it will be return 'RecyclerView.NO_POSITION'
 *
 * @return Please check upper comment
 */
fun RecyclerView.findFirstVisiblePosition(): Int {
    val currentLayoutManager = layoutManager

    return if (currentLayoutManager is LinearLayoutManager) {
        currentLayoutManager.findFirstVisibleItemPosition()
    } else {
        RecyclerView.NO_POSITION
    }
}

/**
 * If recyclerView has LinearLayoutManager (or Extends class),
 * it will be return 'LinearLayoutManager.findLastVisibleItemPosition()'
 *
 * if it's not, it will be return 'RecyclerView.NO_POSITION'
 *
 * @return Please check upper comment
 */
fun RecyclerView.findLastVisiblePosition(): Int {
    val currentLayoutManager = layoutManager

    return if (currentLayoutManager is LinearLayoutManager) {
        currentLayoutManager.findLastVisibleItemPosition()
    } else {
        RecyclerView.NO_POSITION
    }
}

/**
 * Is the entered [position] displayed?
 *
 * @return compare findFirstVisiblePosition() and findLastVisiblePosition()
 */
fun RecyclerView.isVisiblePosition(position: Int): Boolean {
    val firstVisibleItem = findFirstVisiblePosition()
    val lastVisibleItem = findLastVisiblePosition()

    return position in (firstVisibleItem..lastVisibleItem)
}