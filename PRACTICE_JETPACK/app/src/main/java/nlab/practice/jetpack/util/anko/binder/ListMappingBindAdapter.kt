package nlab.practice.jetpack.util.anko.binder

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 */
class ListMappingBindAdapter<T: RecyclerView, U, ITEM>(
        private val mapper: (U)-> List<ITEM>,
        private val listBinder: ListBinder<T, ITEM>) : Binder<U> {

    override fun addCallback(any: U) {
       listBinder.addCallback(mapper(any))
    }

    override fun removeCallback() {
        listBinder.removeCallback()
    }

    override fun notifyChanged() {
        listBinder.notifyChanged()
    }
}