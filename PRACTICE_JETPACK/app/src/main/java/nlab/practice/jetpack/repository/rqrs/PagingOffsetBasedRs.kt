package nlab.practice.jetpack.repository.rqrs

import nlab.practice.jetpack.repository.model.PagingItem

/**
 * @author Doohyun
 */
data class PagingOffsetBasedRs(
        private val total: Long,
        private val limit: Long,
        private val offset: Long,
        private val count: Long,
        private val items: List<PagingItem>
)