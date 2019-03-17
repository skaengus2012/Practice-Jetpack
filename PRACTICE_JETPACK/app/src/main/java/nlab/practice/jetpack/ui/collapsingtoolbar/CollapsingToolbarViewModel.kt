package nlab.practice.jetpack.ui.collapsingtoolbar

import nlab.practice.jetpack.repository.CoverRepository
import nlab.practice.jetpack.repository.PagingItemRepository
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CollapsingToolbarViewModel @Inject constructor(
        val _coverRepositry: CoverRepository,
        val _pagingItemRepositry: PagingItemRepository,
        val _itemViewModelFactory: CollapsingPagingItemViewModelFactory
) {

}