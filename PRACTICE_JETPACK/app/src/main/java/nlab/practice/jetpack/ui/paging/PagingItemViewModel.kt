package nlab.practice.jetpack.ui.paging

import androidx.databinding.Bindable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
class PagingItemViewModel(private val _pagingItem: PagingItem) : BindingItemViewModel() {

    @Bindable
    fun getTitle(): String = _pagingItem.title

    override fun getLayoutRes(): Int = R.layout.view_paging_item
}