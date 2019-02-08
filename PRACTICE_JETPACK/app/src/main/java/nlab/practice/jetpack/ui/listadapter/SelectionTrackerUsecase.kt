package nlab.practice.jetpack.ui.listadapter

import android.view.MotionEvent
import androidx.annotation.MainThread
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.Subject
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewHolder
import nlab.practice.jetpack.util.recyclerview.selection.*

/**
 * @author Doohyun
 * @since 2019. 02. 08
 */
class SelectionTrackerUsecase(private val _viewSupplier: () -> RecyclerView) {

    private val _itemKeyProvider = ListBaseItemKeyProvider<Long>()
    private val _selectionObserver = RxSelectionObserverEx<Long>()

    private var _selectionTracker: SelectionTracker<Long>? = null

    private fun initializeTrackerIfNeeded() {
        if (_selectionTracker == null) {
            val recyclerView = _viewSupplier()

            _selectionTracker = SelectionTracker.Builder(
                    ListAdapterExampleFragment::class.java.simpleName,
                    recyclerView,
                    _itemKeyProvider,
                    ItemDetailsLookupEx(recyclerView),
                    StorageStrategy.createLongStorage())
                    .withOnDragInitiatedListener { true }
                    .build()
                    .apply { addObserver(_selectionObserver) }
        }
    }

    @MainThread
    fun replaceList(items: List<Selectable<Long>>) {
        initializeTrackerIfNeeded()

        _itemKeyProvider.replaceList(items)
    }

    fun getSelectionTracker(): SelectionTracker<Long>? = _selectionTracker

    fun getSelectionEventSubject(): Subject<SelectionEvent<Long>> = _selectionObserver.subject
}

private class ItemDetailsLookupEx(private val _recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        var result: ItemDetails<Long>? = null

        val view = _recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            (_recyclerView.getChildViewHolder(view) as? BindingItemViewHolder)?.run {
                result = createItemDetails(this)
            }
        }

        return result
    }

    private fun createItemDetails(holder: BindingItemViewHolder): ItemDetails<Long>? {
        return holder.currentViewModel
                ?.let { it as? Selectable<*> }
                ?.takeIf { it.getSelectionKey() is Long }
                ?.let { it.getSelectionKey() as? Long }
                ?.run { DefaultItemDetails(this, holder.adapterPosition) }
    }
}