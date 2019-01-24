package nlab.practice.jetpack.util.recyclerview.paging

import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Pageable 을 지원하는 BindingItemViewModel
 *
 * @author Doohyun
 */
abstract class PageableItemViewModel<T: Pageable<T>> constructor(val item: T):
        BindingItemViewModel(),
        Pageable<PageableItemViewModel<T>> {

    override fun areItemsTheSame(newItem: PageableItemViewModel<T>): Boolean = item.areItemsTheSame(newItem.item)

    override fun areContentsTheSame(newItem: PageableItemViewModel<T>): Boolean = item.areContentsTheSame(newItem.item)

    override fun getChangePayload(newItem: PageableItemViewModel<T>): Any? = item.getChangePayload(newItem.item)
}

