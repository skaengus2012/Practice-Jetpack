package nlab.practice.jetpack.util.anko.binder

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
class ListBinder<T: RecyclerView, ITEM>(private val target: T): Binder<List<ITEM>> {

    private var observableList: List<ITEM>? = null
    private var onListChangedCallback = ArrayList<LCallback<ITEM>>()

    override fun addCallback(any: List<ITEM>) {
        observableList = any
        onListChangedCallback.forEach { any.toObservableList()?.addOnListChangedCallback(it) }
    }

    override fun removeCallback() {
        onListChangedCallback.forEach { observableList?.toObservableList()?.removeOnListChangedCallback(it) }
    }

    override fun notifyChanged() {
        onListChangedCallback.forEach { observableList?.toObservableList()?.run { it.onChanged(this) } }
    }

    fun drive(callback: LCallback<ITEM>) : T {
        onListChangedCallback.add(callback)
        observableList?.toObservableList()?.addOnListChangedCallback(callback)

        return target
    }

    private fun List<ITEM>.toObservableList(): ObservableList<ITEM>? {
        @Suppress("UNCHECKED_CAST")
        return this as? ObservableList<ITEM>
    }
}