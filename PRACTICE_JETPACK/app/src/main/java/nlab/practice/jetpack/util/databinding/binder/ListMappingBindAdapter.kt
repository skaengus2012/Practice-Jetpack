package nlab.practice.jetpack.util.databinding.binder

import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 */
class ListMappingBindAdapter<T: RecyclerView, U, ITEM>(
        private val _mapper: (U)-> List<ITEM>,
        private val _listBinder: ListBinder<T, ITEM>) : Binder<U> {

    override fun addCallback(any: U) {
       _listBinder.addCallback(_mapper(any))
    }

    override fun removeCallback() {
        _listBinder.removeCallback()
    }

    override fun notifyChanged() {
        _listBinder.notifyChanged()
    }
}