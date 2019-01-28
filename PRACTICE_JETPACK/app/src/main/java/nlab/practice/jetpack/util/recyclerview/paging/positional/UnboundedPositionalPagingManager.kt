package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.*

/**
 *  전체 사이즈를 알 수 없는 경우, Position 만으로 데이터를 처리하는 Manager
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
class UnboundedPositionalPagingManager<T>
private constructor(
        private val _disposables: CompositeDisposable,
        private val _dataRepository: DataRepository<T>) : PositionalPagingManager<T>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_START, params))

        _dataRepository.getItems(params.requestedStartPosition, params.requestedLoadSize)
                .doOnSuccess {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_FINISH, params))
                    callback.onResult(it, params.requestedStartPosition)
                }
                .doOnError {
                    stateSubject.onNext(PositionalEvent(PositionalDataLoadState.INIT_LOAD_ERROR, params))
                    callback.onResult(Collections.emptyList(), params.requestedStartPosition)
                }
                .subscribe()
                .addTo(_disposables)
    }

    interface DataRepository<T> {
        fun getItems(offset: Int, limit: Int): Single<List<T>>
    }

    class Factory(private val _disposables: CompositeDisposable) {
        fun <T> create(dataRepository: DataRepository<T>): UnboundedPositionalPagingManager<T> {
            return UnboundedPositionalPagingManager(_disposables, dataRepository)
        }
    }
}