package nlab.practice.jetpack.util.databinding.binder

import nlab.practice.jetpack.util.databinding.model.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.anko.AnkoViewBindingItem

/**
 * @author Doohyun
 */
interface ListItemViewModel<ITEM: AnkoViewBindingItem> {
    fun getItems(): List<ITEM>? = null
    fun getHeader(): ITEM? = null
    fun getFooter(): ITEM? = null
    fun getRecyclerViewConfig(): RecyclerViewConfig? = null
}