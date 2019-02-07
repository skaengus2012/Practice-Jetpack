package nlab.practice.jetpack.model

import nlab.practice.jetpack.util.recyclerview.Different

/**
 * @author Doohyun
 * @since 2019. 01. 15
 */
data class PagingItem(private val _id: Int, private val _title: String) : Different<PagingItem> {
    override fun areItemsTheSame(newItem: PagingItem): Boolean {
        return _id == newItem._id
    }

    override fun areContentsTheSame(newItem: PagingItem): Boolean {
        return  areItemsTheSame(newItem) and (_title == newItem._title)
    }

    override fun getChangePayload(newItem: PagingItem): Any? = newItem
}