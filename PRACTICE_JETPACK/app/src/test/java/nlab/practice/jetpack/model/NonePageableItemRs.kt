package nlab.practice.jetpack.model

import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs

/**
 * @author Doohyun
 * @since 2019. 01. 15
 */
class NonePageableItemRs(private val totalCount: Int, private val items: List<NonePageableItem>) :
        CountablePositionalRs<NonePageableItem> {
    override fun getTotalCount(): Int = totalCount
    override fun getItems(): List<NonePageableItem> = items
}