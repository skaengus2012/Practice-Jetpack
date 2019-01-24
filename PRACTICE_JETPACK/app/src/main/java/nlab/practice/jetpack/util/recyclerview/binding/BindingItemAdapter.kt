package nlab.practice.jetpack.util.recyclerview.binding

import android.view.ViewGroup
import nlab.practice.jetpack.util.recyclerview.AbsGenericItemAdapter

/**
 * DataBinding 에 사용되는 RecyclerView Adapter
 *
 * @author Doohyun
 */
class BindingItemAdapter : AbsGenericItemAdapter<BindingItemViewModel, BindingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingItemViewHolder
            = BindingItemViewHolder.create(parent, viewType)

    override fun getItemViewType(position: Int): Int = getItemWithCategory(position)?.getLayoutRes() ?: 0
}