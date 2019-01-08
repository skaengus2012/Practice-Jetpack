package nlab.practice.jetpack.repository

import io.reactivex.Observable
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.repository.rqrs.PagingOffsetBasedRs
import kotlin.random.Random

/**
 * Paging 에서 데이터 소스 타입에 따라 MOCK 데이터 출력
 *
 * Cursor-based Pagination
 * Time-based Pagination
 * Offset-based Pagination
 *
 * @author Doohyun
 */
internal class PagingItemRepository {

    companion object {
        private val ITEM_POOLS: List<PagingItem> = (0..1000).map {
            PagingItem(it, "TITLE (No.$it)")
        }
    }

    private fun sleepRequestDuration() = Random.nextInt(100, 1000).run {
        Thread.sleep(toLong())
    }

    fun getItemCount(): Long {
        sleepRequestDuration()

        return ITEM_POOLS.size.toLong()
    }

    /**
     * Offset 기반의 Paging 정보 출력
     */
    fun getItems(offset: Long, limit: Long): PagingOffsetBasedRs {
        sleepRequestDuration()

        val resultSubList: List<PagingItem> = Observable.fromIterable(ITEM_POOLS)
                .skip(offset)
                .take(limit)
                .toList()
                .blockingGet()


        return PagingOffsetBasedRs(
                total = ITEM_POOLS.size.toLong(),
                limit = limit,
                offset = offset,
                count = resultSubList.size.toLong(),
                items = resultSubList)
    }

}