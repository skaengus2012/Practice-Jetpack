package nlab.practice.jetpack.ui.collapsingtoolbar

import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModel
import nlab.practice.jetpack.ui.common.viewmodel.PagingItemViewModelImpl
import nlab.practice.jetpack.ui.common.viewmodel.PagingTheme
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
@AutoFactory
class CollapsingPagingItemViewModel(private val _pagingItem: PagingItem, private val _onClickAction: () -> Unit) :
        BindingItemViewModel(),
        PagingItemViewModel by PagingItemViewModelImpl(PagingTheme.WHITE, _pagingItem, _onClickAction)