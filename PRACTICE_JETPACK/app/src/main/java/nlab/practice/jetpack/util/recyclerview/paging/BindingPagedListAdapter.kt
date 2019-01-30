package nlab.practice.jetpack.util.recyclerview.paging

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagedListAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewHolder
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Paging 작업을 수행하는 Adapter 정의
 *
 * @author Doohyun
 */
class BindingPagedListAdapter<T: BindingItemViewModel> (
        callback: PageableCallback<T>? = null,
        val bottomMoreItem: BindingItemViewModel? = null,
        @LayoutRes val placeholderResId: Int = 0) : PagedListAdapter<T, BindingItemViewHolder>(callback?: PageableCallbackEx()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingItemViewHolder =
            BindingItemViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: BindingItemViewHolder, position: Int) {
        getItem(position)?.run { holder.onBind(this) }
    }

    override fun getItemViewType(position: Int): Int {
        var item: Int? = getItem(position)?.getLayoutRes()
        if (item == null) {
           item = placeholderResId
        }

        return item
    }
}

