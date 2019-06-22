package nlab.practice.jetpack.paging

import androidx.paging.PositionalDataSource.*
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Countable Positional 페이징을 동작하는 모듈에 대한 테스트
 *
 * @author Doohyun
 * @since 2019. 01. 18
 */
class CountablePositionalPagingTest {

    private val disposables = CompositeDisposable()
    private val repository = TestRepository()
    private val initParam = LoadInitialParams(0, 20, 10, false)
    private val rangeParam = LoadRangeParams(2, 20)

    private lateinit var initCallback: LoadInitialCallback<NonePageableItem>
    private lateinit var rangeCallback: LoadRangeCallback<NonePageableItem>

    private lateinit var pagingManager: CountablePositionalPagingManager<NonePageableItem>

    @Suppress("UNCHECKED_CAST")
    @Before
    fun setup() {
        initCallback = mock(LoadInitialCallback::class.java) as LoadInitialCallback<NonePageableItem>
        rangeCallback = mock(LoadRangeCallback::class.java) as LoadRangeCallback<NonePageableItem>

        pagingManager = CountablePositionalPagingManager
                .Factory(disposables, mock(SchedulerFactory::class.java))
                .create(repository)
    }

    private fun delayLoadTime() {
        Thread.sleep(3000)
    }

    private fun addItemToRepository() {
        Thread.sleep(1000)
        repository.items.add(NonePageableItem())
    }

    @Test
    fun testInitLoadCallbackSuccess() {
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject.subscribe { observeCodes += it.state }

        dataSource.loadInitial(initParam, initCallback)

        Assert.assertEquals(
                listOf(PositionalDataLoadState.INIT_LOAD_START, PositionalDataLoadState.INIT_LOAD_FINISH),
                observeCodes)

    }

    @Test
    fun testInitLoadCallbackDataSizeChanged() {
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject
                .doOnNext { observeCodes += it.state }
                .subscribe()

        Completable.fromAction { dataSource.loadInitial(initParam, initCallback) }
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
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject.subscribe{ observeCodes += it.state }

        dataSource.loadInitial(initParam, initCallback)
        dataSource.loadRange(rangeParam, rangeCallback)

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH,
                PositionalDataLoadState.LOAD_START,
                PositionalDataLoadState.LOAD_FINISH), observeCodes)
    }

    @Test
    fun testLoadRangeCallbackDataSizeChanged() {
        val dataSource = pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        pagingManager.stateSubject
                .doOnNext { observeCodes += it.state }
                .subscribe()

        dataSource.loadInitial(initParam, initCallback)

        Completable.fromAction { dataSource.loadRange(rangeParam, rangeCallback) }
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
