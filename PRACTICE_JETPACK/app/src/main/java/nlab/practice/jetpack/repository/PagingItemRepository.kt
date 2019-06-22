package nlab.practice.jetpack.repository

import io.reactivex.Observable
import io.reactivex.Single
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.RandomTestExecutor
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager

/**
 * Paging 에서 데이터 소스 타입에 따라 MOCK 데이터 출력
 *
 * Cursor-based Pagination
 * Time-based Pagination
 * Offset-based Pagination
 *
 * @author Doohyun
 */
class PagingItemRepository(private val imagePoolRepository: ImagePoolRepository) :
        CountablePositionalPagingManager.DataRepository<PagingItem>,
        UnboundedPositionalPagingManager.DataRepository<PagingItem> {

    private val items = ArrayList<PagingItem>()

    init {
        (0 until 1000).map {
            PagingItem(it, "Track Item (No.$it)", imagePoolRepository.get(it % imagePoolRepository.getSize()))
        }.run {
            items.addAll(this)
        }
    }

    override fun getTotalCount(): Single<Int> = Single.fromCallable {
        RandomTestExecutor.delay(maxTime = 500)
        items.size
    }

    override fun getCountablePositionalRs(offset: Int, limit: Int): Single<PagingItemRs> = Single.fromCallable {
        RandomTestExecutor.delay(maxTime = 1500)

        val resultSubList: List<PagingItem> = Observable.fromIterable(items)
                .skip(offset.toLong())
                .take(limit.toLong())
                .toList()
                .blockingGet()

        // 가상의 에러 방출
        RandomTestExecutor.error(20)

        PagingItemRs(items.size, resultSubList)
    }

    override fun getItems(offset: Int, limit: Int): Single<List<PagingItem>> = Single.fromCallable {
        RandomTestExecutor.delay(maxTime = 1500)

        val resultSubList = Observable.fromIterable(items)
                .skip(offset.toLong())
                .take(limit.toLong())
                .toList()
                .blockingGet()

        // 가상의 에러 방출
        RandomTestExecutor.error(20)

        resultSubList
    }

    fun addItems(items: List<PagingItem>) = this.items.addAll(items)
}

class PagingItemRs(
        private val _totalCount: Int,
        private val _items: List<PagingItem>): CountablePositionalRs<PagingItem> {

    override fun getTotalCount(): Int = _totalCount

    override fun getItems(): List<PagingItem> = _items
}