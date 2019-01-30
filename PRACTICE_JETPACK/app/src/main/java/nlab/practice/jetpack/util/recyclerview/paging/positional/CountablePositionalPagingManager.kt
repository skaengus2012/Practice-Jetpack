package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource
import androidx.paging.PositionalDataSource.*

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
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

    private var _totalCount: Int? = null

    override fun newDataSource(): PositionalDataSource<T> {
        _totalCount = null

        return super.newDataSource()
    }

    /**
     * NOTE : [callback] 의 경우, 페이징 조건에 맞을 경우 호출해야함 그렇지 않을 경우 예외를 발생시킴
     *        LOAD_FINISH 가 등장할 타이밍에 적절한 개수의 아이템이 등장하지 못할 경우에도 예외가 발생
     *
     * 그 이외 상황에 대해서는 subject 를 통해 적절히 처리가 필요함
     */
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        stateSubject.onNext(PositionalEvent(PositionalDataLoadState.LOAD_START, rangeParams = params))

        _dataRepository.getCountablePositionalRs(params.startPosition, params.loadSize)
                .doOnSuccess {
                    val isEqualsTotalCount = _totalCount?: -1 == it.getTotalCount()
                    if (isEqualsTotalCount) {
                        callback.onResult(it.getItems())
                        PositionalEvent(PositionalDataLoadState.LOAD_FINISH, rangeParams = params)
                    } else {
                        PositionalEvent(PositionalDataLoadState.LOAD_DATA_SIZE_CHANGED, rangeParams = params)
                    }.run { stateSubject.onNext(this) }
                }
                .doOnError {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.LOAD_ERROR, rangeParams = params))
                }
                .subscribe()
                .addTo(_disposables)
    }

    /**
     * NOTE : [callback] 의 경우, 페이징 조건에 맞을 경우 호출해야함 그렇지 않을 경우 화면에 그리지 않음
     *        LOAD_FINISH 가 등장할 타이밍에 적절한 개수의 아이템이 등장하지 못할 경우에도 화면에 그리지 않음
     *
     * 그 이외 상황에 대해서는 subject 를 통해 적절히 처리가 필요함
     */
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_START, initParams = params))
        _dataRepository.getTotalCount()
                .doOnSuccess {
                    totalCount
                    ->
                    _totalCount = totalCount

                    if (totalCount == 0) {
                        loadInitialEmpty(params, callback)
                    } else {
                        loadInitialInternal(totalCount, params, callback)
                    }
                }
                .doOnError {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_ERROR, initParams = params))
                }
                .subscribe()
                .addTo(_disposables)
    }

    private fun loadInitialEmpty(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        PositionalEvent(PositionalDataLoadState.INIT_LOAD_FINISH, initParams = params)
        callback.onResult(Collections.emptyList(), params.requestedStartPosition,0)
    }

    private fun loadInitialInternal(totalCount: Int, params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        val firstLoadPosition = PositionalDataSource.computeInitialLoadPosition(params, totalCount)
        val firstLoadSize = PositionalDataSource.computeInitialLoadSize(params, firstLoadPosition, totalCount)

        _dataRepository.getCountablePositionalRs(firstLoadPosition, firstLoadSize)
                .doOnSuccess {
                    val isEqualsTotalCount = (it.getTotalCount() == totalCount)
                    if (isEqualsTotalCount) {
                        callback.onResult(it.getItems(), firstLoadPosition, it.getTotalCount())
                        PositionalEvent(PositionalDataLoadState.INIT_LOAD_FINISH, initParams = params)
                    } else {
                        PositionalEvent(PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED, initParams = params)
                    }.run { stateSubject.onNext(this) }
                }
                .doOnError {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_ERROR, initParams = params))
                }
                .subscribe()
                .addTo(_disposables)
    }

    interface DataRepository<T> {
        fun getTotalCount(): Single<Int>
        fun getCountablePositionalRs(offset: Int, limit: Int): Single<out CountablePositionalRs<T>>
    }

    class Factory(private val _disposables: CompositeDisposable) {
        fun <T> create(dataRepository: DataRepository<T>): CountablePositionalPagingManager<T> {
            return CountablePositionalPagingManager(_disposables, dataRepository)
        }
    }
}