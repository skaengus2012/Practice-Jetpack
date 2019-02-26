package nlab.practice.jetpack.ui.itemtouch

import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.ItemTouchHelper
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.touch.DragEvent
import nlab.practice.jetpack.util.recyclerview.touch.SwipeDeleteTouchEventHelperCallback
import nlab.practice.jetpack.util.recyclerview.touch.VerticalDragItemTouchHelperCallback
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
class ItemTouchHelperViewModel @Inject constructor(
        private val _activityCommonUsecase: ActivityCommonUsecase,
        private val _disposables: CompositeDisposable,
        private val _pagingItemRepository: PagingItemRepository,
        private val _schedulerFactory: SchedulerFactory,
        private val _dragCallback: VerticalDragItemTouchHelperCallback,
        private val _swipeCallback: SwipeDeleteTouchEventHelperCallback,
        private val _touchHelpers: Set<@JvmSuppressWildcards ItemTouchHelper>,
        private val _itemModelFactory: ItemTouchHelperItemViewModelFactory) : ListErrorPageViewModel {

    private val _itemUpdateSubject: BehaviorSubject<List<ItemTouchHelperItemViewModel>>
            = BehaviorSubject.createDefault(emptyList())

    private val _isShowErrorView = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<ItemTouchHelperItemViewModel>()

    val recyclerViewConfig = createRecyclerViewConfig()

    init {
        subscribeItems()
        subscribeDragEvent()
        subscribeSwipeDeleteEvent()
        loadItems()
    }

    private fun createRecyclerViewConfig(): RecyclerViewConfig = RecyclerViewConfig().apply {
        itemTouchHelperSuppliers.addAll(_touchHelpers)
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
                .doOnNext { swapItems(it.fromPosition, it.toPosition) }
                .subscribe()
                .addTo(_disposables)
    }

    private fun subscribeSwipeDeleteEvent() {
        _swipeCallback.eventSubject
                .observeOn(_schedulerFactory.ui())
                .doOnNext { removeItems(it.position) }
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

    private fun swapItems(fromPosition: Int, toPosition: Int) = _itemUpdateSubject.value?.run {
        val currentSize = size

        val isValidSize = fromPosition < currentSize && toPosition < currentSize
        if (isValidSize) {
            ArrayList(this)
                    .apply { Collections.swap(this, fromPosition, toPosition) }
                    .run { _itemUpdateSubject.onNext(this) }
        }
    }

    private fun removeItems(targetIndex: Int) = _itemUpdateSubject.value?.run {
        val isValidSize = targetIndex < size
        if (isValidSize) {
            ArrayList(this)
                    .apply { removeAt(targetIndex) }
                    .run { _itemUpdateSubject.onNext(this) }
        }
    }

    override fun isShowErrorView(): ObservableBoolean = _isShowErrorView

    override fun refresh() {
        loadItems()
    }

    fun onBackPressed() = _activityCommonUsecase.onBackPressed()
}