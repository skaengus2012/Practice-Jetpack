package nlab.practice.jetpack.ui.itemtouch

import androidx.databinding.ObservableBoolean
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.touch.DragMovementItemTouchHelperCallback
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
class ItemTouchHelperViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _pagingItemRepository: PagingItemRepository,
        private val _schedulerFactory: SchedulerFactory,
        private val _itemModelFactory: ItemTouchHelperItemViewModelFactory) : ListErrorPageViewModel {

    private val _dragCallback = DragMovementItemTouchHelperCallback()

    private val _itemUpdateSubject: BehaviorSubject<List<ItemTouchHelperItemViewModel>>
            = BehaviorSubject.createDefault(emptyList())

    private val _isShowErrorView = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<ItemTouchHelperItemViewModel>()

    val recyclerViewConfig = createRecyclerViewConfig()

    init {
        subscribeItems()
        subscribeDragEvent()
        loadItems()
    }

    private fun createRecyclerViewConfig(): RecyclerViewConfig = RecyclerViewConfig().apply {
        itemTouchHelpers.add(_dragCallback)
    }

    private fun subscribeItems() {
        _itemUpdateSubject.observeOn(_schedulerFactory.ui())
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeDragEvent() {
        _dragCallback.eventSubject
                .observeOn(_schedulerFactory.ui())
                .doOnNext { swapItems(it) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun loadItems() {
        _pagingItemRepository.getItems(0, 300)
                .subscribeOn(_schedulerFactory.io())
                .flatMapPublisher { Flowable.fromIterable(it) }
                .map { _itemModelFactory.create(it) }
                .toList()
                .doOnSuccess {
                    _itemUpdateSubject.onNext(it)

                    if (_isShowErrorView.get()) {
                        _isShowErrorView.set(false)
                    }
                }
                .doOnError { _isShowErrorView.set(true) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun swapItems(event: DragMovementItemTouchHelperCallback.DragEvent) = _itemUpdateSubject.value?.run {
        val currentSize = size

        val isValidSize = event.fromPosition < currentSize && event.toPosition < currentSize
        if (isValidSize) {
            val newItems = ArrayList(this)
            Collections.swap(newItems, event.fromPosition, event.toPosition)

            _itemUpdateSubject.onNext(newItems)
        }
    }

    override fun isShowErrorView(): ObservableBoolean = _isShowErrorView

    override fun refresh() {
        loadItems()
    }
}