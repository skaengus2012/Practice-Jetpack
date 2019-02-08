package nlab.practice.jetpack.ui.listadapter

import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.recyclerview.Different
import nlab.practice.jetpack.util.recyclerview.DifferentDelegate
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
@AutoFactory
class ListAdapterExampleItemViewModel(
        private val _pagingItem: PagingItem) :
        BindingItemViewModel(),
        Different<ListAdapterExampleItemViewModel> by DifferentDelegate({ _pagingItem }, { viewModel -> viewModel._pagingItem }) {

    override fun getLayoutRes(): Int = R.layout.view_list_adapter_grid_item

    @Bindable
    fun getImageUrl(): String? = _pagingItem.imageUrl

    @Bindable
    fun getTitle(): String? = _pagingItem.title
}