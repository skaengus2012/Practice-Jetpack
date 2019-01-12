package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource
import androidx.paging.PositionalDataSource.*

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import nlab.practice.jetpack.util.recyclerview.paging.PagingDataLoadState
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
        fun getItems(offset: Int, limit: Int): Single<CountablePositionalRs<T>>
    }

    private var _totalCount: Int? = null

    override fun newDataSource(): PositionalDataSource<T> {
        _totalCount = null

        return super.newDataSource()
    }

    /**
     * Paging 상태에 대해 구독정보를 보내는 Subject
     *
     * 페이징 상태는 PagingDataLoadState 를 참고할 것!
     */
    val stateSubject: PublishSubject<String> = PublishSubject.create()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        stateSubject.onNext(PagingDataLoadState.LOAD_START)

        _dataRepository.getItems(params.startPosition, params.loadSize)
                .doOnSuccess {
                    callback.onResult(it.getItems())

                    val isEqualsTotalCount = _totalCount?: -1 == it.getTotalCount()
                    if (isEqualsTotalCount) {
                        stateSubject.onNext(PagingDataLoadState.LOAD_FINISH)
                    } else {
                        stateSubject.onNext(PagingDataLoadState.LOAD_ERROR)
                    }
                }
                .doOnError { stateSubject.onNext(PagingDataLoadState.LOAD_ERROR) }
                .subscribe()
                .addTo(_disposables)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        stateSubject.onNext(PagingDataLoadState.INIT_LOAD_START)
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
                        PagingDataLoadState.INIT_LOAD_FINISH
                    } else {
                        PagingDataLoadState.INIT_LOAD_ERROR
                    }.run { stateSubject.onNext(this) }
                }
                .doOnError { stateSubject.onNext(PagingDataLoadState.INIT_LOAD_ERROR) }
                .subscribe()
                .addTo(_disposables)
    }

    class Factory(private val _disposables: CompositeDisposable) {
        fun <T> create(dataRepository: DataRepository<T>): CountablePositionalPagingManager<T> {
            return CountablePositionalPagingManager(_disposables, dataRepository)
        }
    }
}