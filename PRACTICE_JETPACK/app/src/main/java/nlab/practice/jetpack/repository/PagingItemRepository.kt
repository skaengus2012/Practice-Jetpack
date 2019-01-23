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
class PagingItemRepository : CountablePositionalPagingManager.DataRepository<PagingItem> {

    companion object {
        private val IMAGE_POOLS: List<String> = listOf(
                "https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg",
                "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/album-cover-flyer-template-0f1557abc84d45d5df797adcedaee519.jpg?ts=1478719448",
                "https://99designs-blog.imgix.net/blog/wp-content/uploads/2017/12/attachment_68585523.jpg?auto=format&q=60&fit=max&w=930",
                "https://t1.daumcdn.net/cfile/tistory/21266735579D869932",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0qMgPDmdWZ-2QeLX8fw4iDbZl0CneWX9EbfFDUeaJ6FQvuxhR",
                "http://notefolio.net/data/img/ea/f0/eaf061b315f6f9e515ea5d49c84ca9d0a0e270a0a50a60c6c3e83a187e5dc112_v1.jpg",
                "https://pbs.twimg.com/media/DS2YpKeVoAA9H0l.jpg",
                "http://www.thefirstmedia.net/news/photo/201704/17638_33626_7.jpg"
        )
    }

    private val _items: List<PagingItem> = (0..1000).map {
        PagingItem(it, "Track Item (No.$it)", IMAGE_POOLS[it % IMAGE_POOLS.size])
    }

    private fun sleepRequestDuration() = Random.nextInt(100, 1000).run {
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