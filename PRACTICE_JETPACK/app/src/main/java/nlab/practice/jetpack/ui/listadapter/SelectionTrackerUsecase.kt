package nlab.practice.jetpack.ui.listadapter

import android.view.MotionEvent
import androidx.annotation.MainThread
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.Subject
import nlab.practice.jetpack.util.lazyPublic
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewHolder
import nlab.practice.jetpack.util.recyclerview.selection.*

/**
 * @author Doohyun
 * @since 2019. 02. 08
 */
class SelectionTrackerUsecase(viewSupplier: () -> RecyclerView) {

    private val itemKeyProvider = ListBaseItemKeyProvider<Long>()

    private val selectionObserver = RxSelectionObserverEx<Long>()

    private var selectionTracker: SelectionTracker<Long>? = null

    private val recyclerView: RecyclerView by lazyPublic(viewSupplier)

    private fun initializeTrackerIfNeeded() {
        if (selectionTracker == null) {
            selectionTracker = SelectionTracker.Builder(
                    ListAdapterExampleFragment::class.java.simpleName,
                    recyclerView,
                    itemKeyProvider,
                    ItemDetailsLookupEx(recyclerView),
                    StorageStrategy.createLongStorage())
                    .withOnDragInitiatedListener { true }
                    .build()
                    .apply { addObserver(selectionObserver) }
        }
    }

    @MainThread
    fun replaceList(items: List<Selectable<Long>>) {
        initializeTrackerIfNeeded()

        itemKeyProvider.replaceList(items)
    }

    fun getSelectionTracker(): SelectionTracker<Long>? = selectionTracker

    fun getSelectionEventSubject(): Subject<SelectionEvent<Long>> = selectionObserver.subject
}

private class ItemDetailsLookupEx(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        var result: ItemDetails<Long>? = null

        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            (recyclerView.getChildViewHolder(view) as? BindingItemViewHolder)?.run {
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