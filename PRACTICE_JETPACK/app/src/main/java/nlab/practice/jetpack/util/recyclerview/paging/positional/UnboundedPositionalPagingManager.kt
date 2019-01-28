package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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