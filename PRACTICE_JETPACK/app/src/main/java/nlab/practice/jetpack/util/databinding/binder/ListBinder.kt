package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

private typealias LCallback<ITEM> = ObservableList.OnListChangedCallback<ObservableList<ITEM>>

/**
 * ObservableList 에 대한 Binder 정의
 *
 * 오직 RecyclerView 타겟에 대해 List Type 을 셋할 수 있음
 *
 * @author Doohyun
 */
class ListBinder<T: RecyclerView, ITEM>(private val _target: T): Binder<List<ITEM>> {

    private var _observableList: List<ITEM>? = null
    private var _onListChangedCallback = ArrayList<LCallback<ITEM>>()

    override fun addCallback(any: List<ITEM>) {
        _observableList = any
        _onListChangedCallback.forEach { any.toObservableList()?.addOnListChangedCallback(it) }
    }

    override fun removeCallback() {
        _onListChangedCallback.forEach { _observableList?.toObservableList()?.removeOnListChangedCallback(it) }
    }

    override fun notifyChanged() {
        _onListChangedCallback.forEach { _observableList?.toObservableList()?.run { it.onChanged(this) } }
    }

    fun drive(callback: LCallback<ITEM>) : T {
        _onListChangedCallback.add(callback)
        _observableList?.toObservableList()?.addOnListChangedCallback(callback)

        return _target
    }

    private fun List<ITEM>.toObservableList(): ObservableList<ITEM>? {
        @Suppress("UNCHECKED_CAST")
        return this as? ObservableList<ITEM>
    }
}