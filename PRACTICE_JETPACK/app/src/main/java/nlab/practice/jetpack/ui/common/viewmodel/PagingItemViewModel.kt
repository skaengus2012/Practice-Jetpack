package nlab.practice.jetpack.ui.common.viewmodel

import androidx.annotation.IntDef
import androidx.annotation.LayoutRes
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.PagingItem

/**
 * @author Doohyun
 */
interface PagingItemViewModel {
    fun getTitle(): String
    fun getImageUrl(): String?
    fun onClick()
    @LayoutRes fun getLayoutRes(): Int
    @PagingTheme fun getPagingTheme(): Int
}

@IntDef(value = [
    PagingTheme.BLACK, PagingTheme.WHITE
])
annotation class PagingTheme {
    companion object {
        const val WHITE = 0
        const val BLACK = 1
    }
}

class PagingItemViewModelImpl(
        @PagingTheme private val _pagingTheme: Int, private val _pagingItem: PagingItem) : PagingItemViewModel {

    override fun getTitle(): String = _pagingItem.title

    override fun getImageUrl(): String? = _pagingItem.imageUrl

    override fun onClick() {
        // nothing work
    }

    @PagingTheme override fun getPagingTheme(): Int = _pagingTheme

    @LayoutRes override fun getLayoutRes(): Int = R.layout.view_paging_item
}