package nlab.practice.jetpack.util.recyclerview.binding

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import nlab.practice.jetpack.util.recyclerview.DiffCallback
import nlab.practice.jetpack.util.recyclerview.DiffCallbackEx

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class BindingItemListAdapter<T: BindingItemViewModel>(
        callback: DiffCallback<T>? = null): ListAdapter<T, BindingItemViewHolder>(callback?: DiffCallbackEx()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingItemViewHolder {
        return BindingItemViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingItemViewHolder, position: Int) {
        getItem(position)?.run { holder.onBind(position, this) }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).getLayoutRes()
}