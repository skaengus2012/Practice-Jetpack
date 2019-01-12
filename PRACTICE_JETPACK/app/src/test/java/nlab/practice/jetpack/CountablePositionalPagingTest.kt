package nlab.practice.jetpack

import androidx.paging.PositionalDataSource.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.model.NonePageableItemRs
import nlab.practice.jetpack.util.recyclerview.paging.PagingDataLoadState
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

const val DATA_LOAD_DELAY_SECOND = 2000L

/**
 * Countable Positional 페이징을 동작하는 모듈에 대한 테스트
 *
 * @author Doohyun
 * @since 2019. 01. 18
 */
class CountablePositionalPagingTest {

    private val _disposables = CompositeDisposable()
    private val _repository = TestRepository()
    private val _initParam = LoadInitialParams(0, 20, 10, false)
    private val _rangeParam = LoadRangeParams(2, 20)

    private lateinit var _initCallback: LoadInitialCallback<NonePageableItem>
    private lateinit var _rangeCallback: LoadRangeCallback<NonePageableItem>

    private lateinit var _pagingManager: CountablePositionalPagingManager<NonePageableItem>

    @Suppress("UNCHECKED_CAST")
    @Before
    fun setup() {
        _initCallback = mock(LoadInitialCallback::class.java) as LoadInitialCallback<NonePageableItem>
        _rangeCallback = mock(LoadRangeCallback::class.java) as LoadRangeCallback<NonePageableItem>

        _pagingManager = CountablePositionalPagingManager
                .Factory(_disposables)
                .create(_repository)
    }

    private fun delayLoadTime() {
        Thread.sleep(3000)
    }

    private fun addItemToRepository() {
        Thread.sleep(1000)
        _repository.items.add(NonePageableItem())
    }

    @Test
    fun testInitLoadCallbackSuccess() {
        val dataSource = _pagingManager.newDataSource()

        val observer = TestObserver<String>().apply { _pagingManager.stateSubject.subscribe(this) }

        dataSource.loadInitial(_initParam, _initCallback)
        observer.assertValues(PagingDataLoadState.INIT_LOAD_START, PagingDataLoadState.INIT_LOAD_FINISH)
    }

    @Test
    fun testInitLoadCallbackFailed() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject
                .doOnNext { observeCodes += it }
                .subscribe()

        Completable.fromAction { dataSource.loadInitial(_initParam, _initCallback) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        addItemToRepository()

        delayLoadTime()
        Assert.assertEquals(listOf(PagingDataLoadState.INIT_LOAD_START, PagingDataLoadState.INIT_LOAD_ERROR), observeCodes)
    }

    @Test
    fun testLoadRangeCallbackSuccess() {
        val dataSource = _pagingManager.newDataSource()

        val observer = TestObserver<String>().apply { _pagingManager.stateSubject.subscribe(this) }

        dataSource.loadInitial(_initParam, _initCallback)
        dataSource.loadRange(_rangeParam, _rangeCallback)

        observer.assertValues(
                PagingDataLoadState.INIT_LOAD_START,
                PagingDataLoadState.INIT_LOAD_FINISH,
                PagingDataLoadState.LOAD_START,
                PagingDataLoadState.LOAD_FINISH)
    }

    @Test
    fun testLoadRangeCallbackFailed() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject
                .doOnNext { observeCodes += it }
                .subscribe()

        dataSource.loadInitial(_initParam, _initCallback)

        Completable.fromAction { dataSource.loadRange(_rangeParam, _rangeCallback) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        addItemToRepository()

        delayLoadTime()
        Assert.assertEquals(listOf(
                PagingDataLoadState.INIT_LOAD_START,
                PagingDataLoadState.INIT_LOAD_FINISH,
                PagingDataLoadState.LOAD_START,
                PagingDataLoadState.LOAD_ERROR), observeCodes)
    }

}

private class TestRepository : CountablePositionalPagingManager.DataRepository<NonePageableItem> {
    val items = ArrayList<NonePageableItem>()

    init {
        (0 until 1000).map { NonePageableItem() }.let { items.addAll(it) }
    }

    override fun getTotalCount(): Single<Int> = Single.just(items.size)
    override fun getItems(offset: Int, limit: Int): Single<CountablePositionalRs<NonePageableItem>> {
        Thread.sleep(DATA_LOAD_DELAY_SECOND)

        return Observable.fromIterable(items)
                .take(limit.toLong())
                .skip(offset.toLong())
                .toList()
                .map { NonePageableItemRs(items.size, it) }
    }
}
