package nlab.practice.jetpack.ui.itemtouch

import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
@AutoFactory
class ItemTouchHelperItemViewModel constructor(
        @Provided private val _dragItemTouchHelper: ItemTouchHelper,
        private val _pagingItem: PagingItem) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_dragging_handle_item

    fun getTitle(): String = _pagingItem.title

    fun getImageUrl(): String? = _pagingItem.imageUrl

    fun startDrag(action: Int) : Boolean = if (action == MotionEvent.ACTION_DOWN) {
        itemViewUsecaseFactory
                ?.itemViewTouchHelperUsecaseFactory()
                ?.create(_dragItemTouchHelper)
                ?.startDrag()

        true
    } else { false }

}