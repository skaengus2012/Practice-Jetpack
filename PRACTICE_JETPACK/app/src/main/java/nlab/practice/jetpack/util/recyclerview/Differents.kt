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

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * Android Differ 에 대한 확장
 */

typealias DiffCallback<T> = DiffUtil.ItemCallback<T>

/**
 * 인터페이스 Different 를 지원하는 Callback.
 */
@Suppress("UNCHECKED_CAST")
class DiffCallbackEx<T> : DiffCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Different<T>)?.areItemsTheSame(newItem)) ?: (oldItem === newItem)
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Different<T>)?.areContentsTheSame(newItem)) ?: (oldItem == newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = (oldItem as? Different<T>)?.getChangePayload(newItem)
}

/**
 * DiffCallbackEx 에서 비교하기 위한 인터페이스
 *
 * @author Doohyun
 */
interface Different<T> {
    /**
     * [newItem] 과 비교하여, 동일 항목인가?
     */
    fun areItemsTheSame(newItem: T): Boolean

    /**
     * [newItem] 과 비교하여, 데이터가 동일한가?
     */
    fun areContentsTheSame(newItem: T): Boolean

    /**
     * [newItem] 과 비교하여,
     *
     * areItemsTheSame -> true, areContentsTheSame -> false 일 경우 호출되며,
     *
     * 변경할 컨텐츠를 반환해야함
     */
   fun getChangePayload(newItem: T): Any? = null
}

/**
 * 타입으로 추가한 [D] 에 대하여, Different 행위를 대신 처리하는 Delegate
 *
 * @param target 비교에 사용할 Different
 * @param compareConverter [T] 를 비교할 [D] 로 변경해주는 컨버터
 */
class DifferentDelegate<T, D: Different<D>>(
    private val target: D,
    private val compareConverter: (target: T) -> D
) : Different<T> {

    override fun areItemsTheSame(newItem: T): Boolean {
        return target.areItemsTheSame(compareConverter(newItem))
    }

    override fun areContentsTheSame(newItem: T): Boolean {
        return target.areContentsTheSame(compareConverter(newItem))
    }

    override fun getChangePayload(newItem: T): Any? {
        return target.getChangePayload(compareConverter(newItem))
            ?.let { payloadTarget ->
                if (target === payloadTarget) {
                    this
                } else {
                    newItem
                }
            }
    }
}