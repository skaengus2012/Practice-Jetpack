package nlab.practice.jetpack.util.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Header, Footer, Content 를 지원하는 어댑터
 *
 * @author Doohyun
 */
abstract class AbsGenericItemAdapter<T, VIEW_HOLDER: AbsGenericItemAdapter.GenericItemViewHolder<T>> :
        RecyclerView.Adapter<VIEW_HOLDER>() {

    var items: MutableList<T>? = null
    var headers: MutableList<T>? = null
    var footers: MutableList<T>? = null

    override fun getItemCount(): Int {
        var itemSize = items?.size ?: 0

        // header & footer 의 사이즈를 고려해서 처리
        headers?.run { itemSize += size }
        footers?.run { itemSize += size }

        return itemSize
    }

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        getItemWithCategory(position)?.run { holder.onBind(this) }
    }

    protected fun getItemWithCategory(position: Int): T? {
        val headerSize = headers?.size ?: 0
        val itemSize = items?.size ?: 0
        val footerSize = footers?.size ?: 0

        val headerContentSize = headerSize + itemSize
        val totalSize = headerContentSize + footerSize

        return when {
            // Content 에 데이터가 존재할 경우
            itemSize != 0 && position in headerSize until headerContentSize -> {
                val contentPosition = position - headerSize

                items?.get(contentPosition)
            }

            // Header 에 데이터가 존재할 경우
            headerSize != 0 && position in 0 until headerSize -> headers?.get(position)

            // footer 에 데이터가 존재할 경우
            footerSize != 0 && position in headerContentSize until totalSize -> {
                val footerPosition = position - headerContentSize

                footers?.get(footerPosition)
            }

            else -> null
        }
    }

    abstract class GenericItemViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: T)
    }
}
