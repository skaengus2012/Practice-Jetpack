package nlab.practice.jetpack.util.recyclerview.touch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

/**
 * @author Doohyun
 */
class SwipeDeleteTouchEventHelperCallback(private vararg val swipeFrags: Int) : ItemTouchHelper.Callback() {

    val eventSubject: PublishSubject<SwipeEvent> = PublishSubject.create()

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return ItemTouchHelper.Callback.makeMovementFlags(0, swipeFrags.reduce { acc, i -> acc or i })
    }

    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction in swipeFrags) {
            eventSubject.onNext(SwipeEvent(viewHolder.adapterPosition))
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}