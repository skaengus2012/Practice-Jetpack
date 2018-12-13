package nlab.practice.jetpack.util.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 에 여러 타입의 아이템을 추가할 수 있는 GenericAdapter 정의
 *
 * @author Doohyun
 * @since 2018. 12. 12
 */
abstract class GenericItemAdapter<T, VIEW_HOLDER: GenericItemAdapter.GenericItemViewHolder<T>> :
        RecyclerView.Adapter<VIEW_HOLDER>() {

    var items: MutableList<T>? = null
    var header: T? = null
    var footer: T? = null

    final override fun getItemCount(): Int {
        var itemSize = items?.size ?: 0

        // header & footer 의 사이즈를 고려해서 처리
        header?.run { ++itemSize }
        footer?.run { ++itemSize }

        return itemSize
    }

    final override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        when(position) {
            0 -> header?: items?.get(0)

            itemCount - 1 ->
                footer?: items?.run { get(size - 1) }

            else -> {
                val findPosition = header?.let { position - 1 } ?: position
                items?.get(findPosition)
            }
        }?.run { holder.onBind(this) }
    }

    open fun notifyItemViewTypeChanged() {}

    abstract class GenericItemViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: T)
    }
}