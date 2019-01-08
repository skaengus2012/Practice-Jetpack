package nlab.practice.jetpack.util.recyclerview.binding.paging

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil.ItemCallback 을 생성하는 팩토리 정의
 *
 * @author Doohyun
 * @since 2019. 01. 10
 */
class PagingItemCallbackFactory {

    fun <T : PageableItemViewModel<Any>> createPagingableItemViewModelType(): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem.areItemsTheSame(newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem.areContentsTheSame(newItem)

        override fun getChangePayload(oldItem: T, newItem: T): Any? = oldItem.getChangePayload(newItem)
    }

}