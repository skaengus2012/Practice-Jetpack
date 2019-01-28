package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 * @since 2019. 01. 15
 */
abstract class PositionalPagingManager<T> {
    var dataSource: PositionalDataSource<T>? = null
    val stateSubject: PublishSubject<PositionalEvent> = PublishSubject.create()

    abstract fun loadRange(params: PositionalDataSource.LoadRangeParams, callback: PositionalDataSource.LoadRangeCallback<T>)
    abstract fun loadInitial(params: PositionalDataSource.LoadInitialParams, callback: PositionalDataSource.LoadInitialCallback<T>)

    fun invalidate() {
        dataSource?.invalidate()
    }

    open fun newDataSource(): PositionalDataSource<T> {
        val newDataSource = PositionalDataSourceEx(this)
        dataSource = newDataSource
        return newDataSource
    }

    class PositionalDataSourceEx<T>
    internal constructor(private val _pagingManager: PositionalPagingManager<T>) : PositionalDataSource<T>() {

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
            _pagingManager.loadRange(params, callback)
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
            _pagingManager.loadInitial(params, callback)
        }
    }
}
