package nlab.practice.jetpack.ui.paging

import androidx.paging.PositionalDataSource
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.PagingDataSourceRepository
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.repository.rqrs.PagingOffsetBasedRs
import java.util.*
import javax.inject.Inject

private typealias SingleResult = Single<PagingOffsetBasedRs>

/**
 * PagingItem 제공을 Position 기반으로 처리하는 Data Source
 *
 * @author Doohyun
 */
@AutoFactory
class PagingItemPositionalDataSource @Inject constructor(
        @Provided private val _pagingDataSourceRepository: PagingDataSourceRepository,
        @Provided private val _disposables: CompositeDisposable) : PositionalDataSource<PagingItem>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PagingItem>) {
        loadRangeInternal(params.startPosition.toLong(), params.loadSize.toLong())
                .doOnSuccess { callback.onResult(it.items) }
                .subscribe()
                .addTo(_disposables)
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PagingItem>) {
        _pagingDataSourceRepository.getItemTotalCount()
                .map { it.toInt() }
                .doOnSuccess {
                    totalCount
                    ->
                    if (totalCount == 0) {
                        callback.onResult(Collections.emptyList(), 0,0)
                    } else {
                        val firstLoadPosition = computeInitialLoadPosition(params, totalCount)
                        val firstLoadSize = computeInitialLoadSize(params, firstLoadPosition, totalCount)

                        loadRangeInternal(firstLoadPosition.toLong(), firstLoadSize.toLong())
                                .filter { it.count == firstLoadSize.toLong() }
                                .doOnSuccess { callback.onResult(it.items, firstLoadPosition, totalCount) }
                                .doOnComplete { invalidate() }
                                .subscribe()
                                .addTo(_disposables)
                    }
                }
                .doOnError { invalidate() }
                .subscribe()
                .addTo(_disposables)
    }



    private fun loadRangeInternal(position: Long, totalSize: Long): SingleResult =
            _pagingDataSourceRepository.getOffsetBasedItems(position, totalSize).doOnError { invalidate() }
}