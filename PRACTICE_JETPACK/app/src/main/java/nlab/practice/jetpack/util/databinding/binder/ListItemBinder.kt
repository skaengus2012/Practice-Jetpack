package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

private typealias LCallback<ITEM> = ObservableList.OnListChangedCallback<ObservableList<ITEM>>

/**
 * @author Doohyun
 */
class ListItemBinder<T: RecyclerView, LIST: ObservableList<ITEM>, ITEM>(private val _target: T): Binder<LIST> {

    private var _observableList: LIST? = null
    private var _onListChangedCallback = ArrayList<LCallback<ITEM>>()

    override fun addCallback(any: LIST) {
        _observableList = any
        _onListChangedCallback.forEach { any.addOnListChangedCallback(it) }
    }

    override fun removeCallback() {
        _onListChangedCallback.forEach { _observableList?.removeOnListChangedCallback(it) }
    }

    override fun notifyChanged() {
        _onListChangedCallback.forEach { _observableList?.run { it.onChanged(this) } }
    }

    fun drive(callback: LCallback<ITEM>) : T {
        _onListChangedCallback.add(callback)
        _observableList?.addOnListChangedCallback(callback)

        return _target
    }
}