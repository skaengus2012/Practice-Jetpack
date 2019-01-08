package nlab.practice.jetpack.util.recyclerview.binding.paging

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewHolder
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Paging 작업을 수행하는 Adapter 정의
 *
 * @author Doohyun
 */
class BindingPagedListAdapter<T: BindingItemViewModel> private constructor(callback: PageableCallback<T>) :
        PagedListAdapter<T, BindingItemViewHolder>(callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingItemViewHolder =
            BindingItemViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: BindingItemViewHolder, position: Int) {
        getItem(position)?.run { holder.onBind(this) }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.getLayoutRes() ?: 0
    }

    companion object {
        fun <T: BindingItemViewModel> create(): BindingPagedListAdapter<T> {
            return BindingPagedListAdapter(PageableCallbackEx())
        }

        fun <T: BindingItemViewModel> create(callback: PageableCallback<T>): BindingPagedListAdapter<T> {
            return BindingPagedListAdapter(callback)
        }
    }
}

