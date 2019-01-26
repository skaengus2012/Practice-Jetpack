package nlab.practice.jetpack

import androidx.paging.PositionalDataSource.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.model.NonePageableItemRs
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
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
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject.subscribe { observeCodes += it.state }

        dataSource.loadInitial(_initParam, _initCallback)

        Assert.assertEquals(
                listOf(PositionalDataLoadState.INIT_LOAD_START, PositionalDataLoadState.INIT_LOAD_FINISH),
                observeCodes)

    }

    @Test
    fun testInitLoadCallbackDataSizeChanged() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject
                .doOnNext { observeCodes += it.state }
                .subscribe()

        Completable.fromAction { dataSource.loadInitial(_initParam, _initCallback) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        addItemToRepository()

        delayLoadTime()

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED), observeCodes)
    }

    @Test
    fun testLoadRangeCallbackSuccess() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject.subscribe{ observeCodes += it.state }

        dataSource.loadInitial(_initParam, _initCallback)
        dataSource.loadRange(_rangeParam, _rangeCallback)

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH,
                PositionalDataLoadState.LOAD_START,
                PositionalDataLoadState.LOAD_FINISH), observeCodes)
    }

    @Test
    fun testLoadRangeCallbackDataSizeChanged() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject
                .doOnNext { observeCodes += it.state }
                .subscribe()

        dataSource.loadInitial(_initParam, _initCallback)

        Completable.fromAction { dataSource.loadRange(_rangeParam, _rangeCallback) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        addItemToRepository()

        delayLoadTime()
        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH,
                PositionalDataLoadState.LOAD_START,
                PositionalDataLoadState.LOAD_DATA_SIZE_CHANGED), observeCodes)
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
