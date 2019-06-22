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
        @Provided private val fragmentNavUsecase: FragmentNavUsecase,
        private val pagingItem: PagingItem,
        private val onClickListener: (FragmentNavUsecase) -> Unit) :
        BindingItemViewModel(),
        PagingItemViewModel by PagingItemViewModelImpl(PagingTheme.BLACK, pagingItem, {onClickListener(fragmentNavUsecase)})