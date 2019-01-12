package nlab.practice.jetpack.repository

import io.reactivex.Single
import nlab.practice.jetpack.repository.rqrs.PagingOffsetBasedRs

/**
 * Paging 에서 데이터 소스 타입에 따라 MOCK 데이터 출력
 *
 * Cursor-based Pagination
 * Time-based Pagination
 * Offset-based Pagination
 *
 * @author Doohyun
 */
class PagingDataSourceRepository internal constructor(private val _pagingItemRepository: PagingItemRepository) {

    fun getOffsetBasedItems(offset: Long, limit: Long) : Single<PagingOffsetBasedRs> = Single.fromCallable {

        _pagingItemRepository.getItems(offset, limit)
    }

    fun getItemTotalCount(): Single<Long> = Single.fromCallable { _pagingItemRepository.getItemCount() }

}