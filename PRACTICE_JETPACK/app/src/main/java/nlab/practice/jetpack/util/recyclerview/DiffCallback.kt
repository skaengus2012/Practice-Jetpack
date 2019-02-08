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

