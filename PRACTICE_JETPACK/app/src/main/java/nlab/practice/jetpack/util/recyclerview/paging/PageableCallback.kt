package nlab.practice.jetpack.util.recyclerview.paging

import androidx.recyclerview.widget.DiffUtil

/**
 * PageableCallback 에 대한 기본 구현 정의
 *
 * @author Doohyun
 * @since 2019. 01. 11
 */

typealias PageableCallback<T> = DiffUtil.ItemCallback<T>

@Suppress("UNCHECKED_CAST")
class PageableCallbackEx<T> : PageableCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Pageable<T>)?.areItemsTheSame(newItem)) ?: (oldItem == newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return ((oldItem as? Pageable<T>)?.areContentsTheSame(newItem)) ?: areItemsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? = (oldItem as? Pageable<T>)?.getChangePayload(newItem)
}

