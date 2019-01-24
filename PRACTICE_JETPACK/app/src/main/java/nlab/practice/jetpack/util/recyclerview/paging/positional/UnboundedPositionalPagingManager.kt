package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.paging.PositionalDataSource

/**
 *  전체 사이즈를 알 수 없는 경우, Position 만으로 데이터를 처리하는 Manager
 *
 * @author Doohyun
 * @since 2019. 01. 15
 */
class UnboundedPositionalPagingManager<T> : PositionalPagingManager<T>() {

    override fun loadRange(params: PositionalDataSource.LoadRangeParams, callback: PositionalDataSource.LoadRangeCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadInitial(params: PositionalDataSource.LoadInitialParams, callback: PositionalDataSource.LoadInitialCallback<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}