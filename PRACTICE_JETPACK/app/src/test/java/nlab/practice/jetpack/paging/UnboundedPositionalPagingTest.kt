package nlab.practice.jetpack.paging

import androidx.paging.PositionalDataSource.*
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalDataLoadState
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Unbounded Positional 페이징을 동작하는 모듈에 대한 테스트
 *
 * @author Doohyun
 */
class UnboundedPositionalPagingTest {
    private val _disposables = CompositeDisposable()
    private val _repository = TestRepository()
    private val _initParam = LoadInitialParams(0, 20, 10, false)
    private val _rangeParam = LoadRangeParams(2, 20)

    private lateinit var _initCallback: LoadInitialCallback<NonePageableItem>
    private lateinit var _rangeCallback: LoadRangeCallback<NonePageableItem>

    private lateinit var _pagingManager: UnboundedPositionalPagingManager<NonePageableItem>

    @Suppress("UNCHECKED_CAST")
    @Before
    fun setup() {
        _initCallback = mock(LoadInitialCallback::class.java) as LoadInitialCallback<NonePageableItem>
        _rangeCallback = mock(LoadRangeCallback::class.java) as LoadRangeCallback<NonePageableItem>

        _pagingManager = UnboundedPositionalPagingManager
                .Factory(_disposables)
                .create(_repository)
    }

    @Test
    fun testInitLoadCallbackSuccess() {
        val dataSource = _pagingManager.newDataSource()
        val observeCodes = ArrayList<String>()

        _pagingManager.stateSubject.subscribe { observeCodes += it.state }

        dataSource.loadInitial(_initParam, _initCallback)

        Assert.assertEquals(listOf(
                PositionalDataLoadState.INIT_LOAD_START,
                PositionalDataLoadState.INIT_LOAD_FINISH), observeCodes)
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
}