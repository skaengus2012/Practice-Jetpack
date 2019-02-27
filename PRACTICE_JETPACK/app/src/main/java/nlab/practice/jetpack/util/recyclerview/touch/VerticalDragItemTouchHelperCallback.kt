package nlab.practice.jetpack.util.recyclerview.touch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
class VerticalDragItemTouchHelperCallback(private val _isLongPressDragEnabled: Boolean = true) : ItemTouchHelper.Callback() {
    val eventSubject: PublishSubject<DragEvent> = PublishSubject.create()

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean {

        eventSubject.onNext(DragEvent(viewHolder.adapterPosition, target.adapterPosition))
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFrags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        return ItemTouchHelper.Callback.makeMovementFlags(dragFrags, 0)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun isLongPressDragEnabled(): Boolean = _isLongPressDragEnabled
}