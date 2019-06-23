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

import androidx.recyclerview.selection.ItemDetailsLookup

/**
 * @author Doohyun
 */
class DefaultItemDetails<T>(private val key: T, private val position: Int) : ItemDetailsLookup.ItemDetails<T>() {
    override fun getSelectionKey(): T? = key
    override fun getPosition(): Int = position
}