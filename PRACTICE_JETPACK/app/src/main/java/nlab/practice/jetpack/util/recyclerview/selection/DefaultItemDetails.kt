package nlab.practice.jetpack.util.recyclerview.selection

import androidx.recyclerview.selection.ItemDetailsLookup

/**
 * @author Doohyun
 */
class DefaultItemDetails<T>(private val key: T, private val position: Int) : ItemDetailsLookup.ItemDetails<T>() {
    override fun getSelectionKey(): T? = key
    override fun getPosition(): Int = position
}