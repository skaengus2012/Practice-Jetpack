package nlab.practice.jetpack.repository.model

import nlab.practice.jetpack.util.recyclerview.Different

/**
 * @author Doohyun
 */
data class PagingItem(val itemId: Int, val title: String, val imageUrl: String? = null) : Different<PagingItem> {

    override fun areItemsTheSame(newItem: PagingItem): Boolean = (itemId == newItem.itemId)

    override fun areContentsTheSame(newItem: PagingItem): Boolean = (title == newItem.title)

    override fun getChangePayload(newItem: PagingItem): Any? = newItem
}