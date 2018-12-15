package nlab.practice.jetpack.ui.common

import nlab.practice.jetpack.util.databinding.model.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.anko.AnkoViewBindingItem

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
interface MainContainerViewModel {
    fun getItems(): List<AnkoViewBindingItem>? = null
    fun getHeader(): AnkoViewBindingItem? = null
    fun getFooter(): AnkoViewBindingItem? = null
    fun getRecyclerViewConfig(): RecyclerViewConfig? = null
}