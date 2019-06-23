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

package nlab.practice.jetpack.util.recyclerview.selection

import android.util.SparseArray
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

/**
 * 아이템 목록을 기반으로 key-Value 를 제공하는 Provider
 *
 * @author Doohyun
 */
class ListBaseItemKeyProvider<T>: ItemKeyProvider<T>(SCOPE_CACHED) {

    private val positionToKeys = SparseArray<T>()

    private val keyToPositionGroup = HashMap<T, Int>()

    override fun getPosition(key: T): Int = keyToPositionGroup[key]?: RecyclerView.NO_POSITION

    override fun getKey(position: Int): T? = positionToKeys[position]

    fun replaceList(newItems: List<Selectable<T>>) {
        positionToKeys.clear()
        keyToPositionGroup.clear()

        (0 until newItems.size).forEach {
            val key = newItems[it].getSelectionKey()

            positionToKeys.append(it, key)
            keyToPositionGroup[key] = it
        }
    }
}