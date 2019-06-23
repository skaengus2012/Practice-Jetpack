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

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffCallback 에 대한 기본 구현 정의
 *
 * @author Doohyun
 * @since 2019. 01. 11
 */

typealias DiffCallback<T> = DiffUtil.ItemCallback<T>

@Suppress("UNCHECKED_CAST")
class DiffCallbackEx<T> : DiffCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Different<T>)?.areItemsTheSame(newItem)) ?: (oldItem == newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Different<T>)?.areContentsTheSame(newItem)) ?: areItemsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = (oldItem as? Different<T>)?.getChangePayload(newItem)
}

