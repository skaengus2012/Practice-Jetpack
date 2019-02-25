package nlab.practice.jetpack.ui.itemtouch

import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 02. 25
 */
@AutoFactory
class ItemTouchHelperItemViewModel constructor(private val _pagingItem: PagingItem) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_dragging_handle_item

    fun getTitle(): String = _pagingItem.title

    fun getImageUrl(): String? = _pagingItem.imageUrl
}