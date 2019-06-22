package nlab.practice.jetpack.model

import nlab.practice.jetpack.util.recyclerview.Different

/**
 * @author Doohyun
 * @since 2019. 01. 15
 */
data class PagingItem(private val id: Int, private val title: String) : Different<PagingItem> {
    override fun areItemsTheSame(newItem: PagingItem): Boolean {
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: PagingItem): Boolean {
        return  areItemsTheSame(newItem) and (title == newItem.title)
    }

    override fun getChangePayload(newItem: PagingItem): Any? = newItem
}