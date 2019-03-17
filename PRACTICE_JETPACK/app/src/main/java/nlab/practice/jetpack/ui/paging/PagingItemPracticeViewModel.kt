package nlab.practice.jetpack.ui.paging

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModel
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModelImpl
import nlab.practice.jetpack.ui.common.viewmodel.PagingTheme
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
@AutoFactory
class PagingItemPracticeViewModel(
        @Provided private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _pagingItem: PagingItem,
        private val _onClickListener: (FragmentNavUsecase) -> Unit)
    : BindingItemViewModel(),
        PagingItemViewModel by PagingItemViewModelImpl(_pagingItem = _pagingItem, _pagingTheme = PagingTheme.BLACK) {

    override fun onClick() {
        _onClickListener(_fragmentNavUsecase)
    }
}