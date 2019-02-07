package nlab.practice.jetpack.ui.listadapter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class ListAdapterExampleViewModel @Inject constructor(
        layoutManagerFactory: LayoutManagerFactory,
        itemDecoration: ListAdapterExampleItemDecoration,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _pagingItemRepository: PagingItemRepository,
        private val _listAdapterItemFactory: ListAdapterExampleItemViewModelFactory) {

    companion object {
        const val SPAN_COUNT = 2
    }

    private val _listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.create()

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val recyclerViewConfig: RecyclerViewConfig

    init {
        initializeList()

        recyclerViewConfig = RecyclerViewConfig().apply {
            layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
            itemDecorations.add(itemDecoration)
        }
    }

    private fun initializeList() {
        _listUpdateSubject
                .observeOn(_schedulerFactory.ui())
                .subscribe { listAdapter.submitList(it) }
                .addTo(_disposables)

        loadList()
    }

    private fun loadList() {
        _pagingItemRepository.getItems(0, 200)
                .subscribeOn(_schedulerFactory.io())
                .doOnSuccess {
                    list
                    ->
                    list.map { _listAdapterItemFactory.create(it) }.run { _listUpdateSubject.onNext(this) }
                }
                .subscribe()
                .addTo(_disposables)
    }
}