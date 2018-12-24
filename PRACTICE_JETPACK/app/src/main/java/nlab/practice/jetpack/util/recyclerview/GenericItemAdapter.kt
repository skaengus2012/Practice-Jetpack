package nlab.practice.jetpack.util.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Doohyun
 */
abstract class GenericItemAdapter<T, VIEW_HOLDER: GenericItemAdapter.GenericItemViewHolder<T>> :
        RecyclerView.Adapter<VIEW_HOLDER>() {

    var contents: MutableList<T>? = null
    var headers: MutableList<T>? = null
    var footers: MutableList<T>? = null

    override fun getItemCount(): Int {
        var itemSize = contents?.size ?: 0

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
        val contentSize = contents?.size ?: 0
        val footerSize = footers?.size ?: 0

        val headerContentSize = headerSize + contentSize
        val totalSize = headerContentSize + footerSize

        return when {
            // Content 에 데이터가 존재할 경우
            contentSize != 0 && position in headerSize until contentSize -> {
                val contentPosition = position - headerSize

                contents?.get(contentPosition)
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
