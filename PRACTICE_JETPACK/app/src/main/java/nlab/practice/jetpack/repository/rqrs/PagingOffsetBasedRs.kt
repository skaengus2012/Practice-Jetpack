package nlab.practice.jetpack.repository.rqrs

import nlab.practice.jetpack.repository.model.PagingItem

/**
 * @author Doohyun
 */
data class PagingOffsetBasedRs(
        val total: Long,
        val limit: Long,
        val offset: Long,
        val count: Long,
        val items: List<PagingItem>
)