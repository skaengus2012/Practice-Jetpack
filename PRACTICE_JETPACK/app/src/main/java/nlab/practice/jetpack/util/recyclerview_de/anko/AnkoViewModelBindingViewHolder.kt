package nlab.practice.jetpack.util.recyclerview_de.anko

import android.view.View
import nlab.practice.jetpack.util.recyclerview_de.GenericItemAdapter

/**
 * 일반적인 Item 에 대한 ViewHolder
 *
 * @author Doohyun
 */
class AnkoViewModelBindingViewHolder<T: AnkoViewBindingItem>(view: View) :
        GenericItemAdapter.GenericItemViewHolder<T>(view) {

    override fun onBind(item: T) = item.notifyChange()
}

