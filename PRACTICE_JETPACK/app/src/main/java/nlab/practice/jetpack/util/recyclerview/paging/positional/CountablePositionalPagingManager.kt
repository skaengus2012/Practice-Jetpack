package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.annotation.StringDef
import androidx.paging.PositionalDataSource
import androidx.paging.PositionalDataSource.*

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * 전체 사이즈 조회 후, 그 기반으로 Position 을 체크하는 Paging Manager 정의
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
class CountablePositionalPagingManager<T>
private constructor(
        private val _disposables: CompositeDisposable,
        private val _dataRepository: DataRepository<T>) : PositionalPagingManager<T>() {

    interface DataRepository<T> {
        fun getTotalCount(): Single<Int>
        fun getItems(offset: Int, limit: Int): Single<out CountablePositionalRs<T>>
    }

    private var _totalCount: Int? = null

    override fun newDataSource(): PositionalDataSource<T> {
        _totalCount = null

        return super.newDataSource()
    }

    /**
     * Paging 상태에 대해 구독정보를 보내는 Subject
     *
     * 페이징 상태는 CountablePositionalPagingManager.DataLoadState 를 참고할 것!
     */
    val stateSubject: PublishSubject<String> = PublishSubject.create()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        stateSubject.onNext(DataLoadState.LOAD_START)

        _dataRepository.getItems(params.startPosition, params.loadSize)
                .doOnSuccess {
                    callback.onResult(it.getItems())

                    val isEqualsTotalCount = _totalCount?: -1 == it.getTotalCount()
                    if (isEqualsTotalCount) {
                        stateSubject.onNext(DataLoadState.LOAD_FINISH)
                    } else {
                        stateSubject.onNext(DataLoadState.LOAD_DATA_SIZE_CHANGED)
                    }
                }
                .doOnError { stateSubject.onNext(DataLoadState.LOAD_ERROR) }
                .subscribe()
                .addTo(_disposables)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        stateSubject.onNext(DataLoadState.INIT_LOAD_START)
        _dataRepository.getTotalCount()
                .doOnSuccess {
                    totalCount
                    ->
                    _totalCount = totalCount

                    if (totalCount == 0) {
                        loadInitialEmpty(callback)
                    } else {
                        loadInitialInternal(totalCount, params, callback)
                    }
                }
                .subscribe()
                .addTo(_disposables)
    }

    private fun loadInitialEmpty(callback: LoadInitialCallback<T>) {
        callback.onResult(Collections.emptyList(), 0,0)
    }

    private fun loadInitialInternal(totalCount: Int, params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        val firstLoadPosition = PositionalDataSource.computeInitialLoadPosition(params, totalCount)
        val firstLoadSize = PositionalDataSource.computeInitialLoadSize(params, firstLoadPosition, totalCount)
        _dataRepository.getItems(firstLoadPosition, firstLoadSize)
                .doOnSuccess {
                    callback.onResult(it.getItems(), firstLoadPosition, it.getTotalCount())

                    if (it.getTotalCount() == totalCount) {
                        DataLoadState.INIT_LOAD_FINISH
                    } else {
                        DataLoadState.INIT_LOAD_DATA_SIZE_CHANGED
                    }.run { stateSubject.onNext(this) }
                }
                .doOnError { stateSubject.onNext(DataLoadState.INIT_LOAD_ERROR) }
                .subscribe()
                .addTo(_disposables)
    }

    class Factory(private val _disposables: CompositeDisposable) {
        fun <T> create(dataRepository: DataRepository<T>): CountablePositionalPagingManager<T> {
            return CountablePositionalPagingManager(_disposables, dataRepository)
        }
    }

    /**
     * 데이터의 조회 상태 열람
     */
    @StringDef(value = [
        DataLoadState.INIT_LOAD_START,
        DataLoadState.INIT_LOAD_FINISH,
        DataLoadState.INIT_LOAD_ERROR,
        DataLoadState.INIT_LOAD_DATA_SIZE_CHANGED,
        DataLoadState.LOAD_START,
        DataLoadState.LOAD_FINISH,
        DataLoadState.LOAD_ERROR,
        DataLoadState.LOAD_DATA_SIZE_CHANGED
    ])
    annotation class DataLoadState {
        companion object {
            private const val TAG = "CountablePositionalDataLoadState"
            const val INIT_LOAD_START = "${TAG}_init_start"
            const val INIT_LOAD_FINISH = "${TAG}_init_load_finish"
            const val INIT_LOAD_ERROR = "${TAG}_init_load_error"
            const val INIT_LOAD_DATA_SIZE_CHANGED = "${TAG}_init_load_size_changed"
            const val LOAD_START = "${TAG}_start"
            const val LOAD_FINISH = "${TAG}_finish"
            const val LOAD_ERROR = "${TAG}_error"
            const val LOAD_DATA_SIZE_CHANGED = "${TAG}_load_size_changed"
        }
    }
}