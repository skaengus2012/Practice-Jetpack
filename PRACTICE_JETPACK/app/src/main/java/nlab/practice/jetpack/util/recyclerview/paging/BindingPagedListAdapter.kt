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
class BindingPagedListAdapter<T: BindingItemViewModel> private constructor(
        callback: PageableCallback<T>,
        @LayoutRes val placeholderResId: Int = 0) : PagedListAdapter<T, BindingItemViewHolder>(callback) {

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

    companion object {
        fun <T: BindingItemViewModel> create(
                callback: PageableCallback<T>? = null,
                @LayoutRes placeholderResId: Int = 0): BindingPagedListAdapter<T> {
            return BindingPagedListAdapter(
                    callback = callback?: PageableCallbackEx(),
                    placeholderResId = placeholderResId)
        }
    }
}

