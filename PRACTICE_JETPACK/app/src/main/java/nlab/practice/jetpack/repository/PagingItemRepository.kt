package nlab.practice.jetpack.repository

import io.reactivex.Observable
import io.reactivex.Single
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs
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
class PagingItemRepository(private val _imagePoolRepository: ImagePoolRepository) : CountablePositionalPagingManager.DataRepository<PagingItem> {

    private val _items: List<PagingItem> = (0..1000).map {
        PagingItem(it, "Track Item (No.$it)", _imagePoolRepository.get(it % _imagePoolRepository.getSize()))
    }

    private fun sleepRequestDuration() = Random.nextInt(200, 1000).run {
        Thread.sleep(toLong())
    }

    override fun getTotalCount(): Single<Int> = Single.fromCallable {
        sleepRequestDuration()
        _items.size
    }

    override fun getItems(offset: Int, limit: Int): Single<PagingItemRs> = Single.fromCallable {
        sleepRequestDuration()

        val resultSubList: List<PagingItem> = Observable.fromIterable(_items)
                .skip(offset.toLong())
                .take(limit.toLong())
                .toList()
                .blockingGet()

        PagingItemRs(_totalCount = _items.size, _items = resultSubList)
    }
}

class PagingItemRs(
        private val _totalCount: Int,
        private val _items: List<PagingItem>): CountablePositionalRs<PagingItem> {

    override fun getTotalCount(): Int = _totalCount

    override fun getItems(): List<PagingItem> = _items
}