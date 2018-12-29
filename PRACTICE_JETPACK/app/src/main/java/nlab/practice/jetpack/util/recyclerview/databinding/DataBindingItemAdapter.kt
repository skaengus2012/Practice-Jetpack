package nlab.practice.jetpack.util.recyclerview.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import nlab.practice.jetpack.util.recyclerview.GenericItemAdapter

private typealias ViewHolder = DataBindingItemViewHolder<DataBindingItemViewModel>

/**
 * DataBinding 에 사용되는 RecyclerView Adapter
 *
 * @author Doohyun
 */
class DataBindingItemAdapter :
        GenericItemAdapter<DataBindingItemViewModel, ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val viewDataBinding =
                DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        return DataBindingItemViewHolder(viewDataBinding)
    }

    override fun getItemViewType(position: Int): Int = getItemWithCategory(position)?.getLayoutRes() ?: 0
}