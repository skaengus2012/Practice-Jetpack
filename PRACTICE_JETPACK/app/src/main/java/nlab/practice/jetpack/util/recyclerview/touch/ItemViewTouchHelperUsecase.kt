package nlab.practice.jetpack.util.recyclerview.touch

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
@AutoFactory
class ItemViewTouchHelperUsecase(
        @Provided private val _viewHolder: RecyclerView.ViewHolder,
        private val _itemViewTouchHelper: ItemTouchHelper) {

    fun startDrag() {
        _itemViewTouchHelper.startDrag(_viewHolder)
    }
}