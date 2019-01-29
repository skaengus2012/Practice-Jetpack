package nlab.practice.jetpack.ui.paging

import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
@AutoFactory
class PagingItemViewModel(
        @Provided private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _pagingItem: PagingItem) : BindingItemViewModel() {

    @Bindable
    fun getTitle(): String = _pagingItem.title

    @Bindable
    fun getImageUrl(): String? = _pagingItem.imageUrl

    override fun getLayoutRes(): Int = R.layout.view_paging_item
}