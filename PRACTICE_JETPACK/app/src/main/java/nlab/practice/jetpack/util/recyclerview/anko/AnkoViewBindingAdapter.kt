package nlab.practice.jetpack.util.recyclerview.anko

import android.view.ViewGroup
import nlab.practice.jetpack.util.recyclerview.GenericItemAdapter
import nlab.practice.jetpack.util.recyclerview.NotFoundViewTypeException
import nlab.practice.jetpack.util.fromViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

private const val HEADER_VIEW_TYPE = 0
private const val FOOTER_VIEW_TYPE = 1
private const val VIEW_TYPE_START_INDEX = 2

/**
 * AnkoComponent 가 사용할 태그명
 *
 * @return 해당 클래스의 네임
 */
private fun AnkoComponent<ViewGroup>.getTag(): String = this::class.java.name

/**
 * Generic 뷰모댈을 담을 수 있는 RecyclerView Adapter
 *
 * @author Doohyun
 */
class AnkoViewBindingAdapter<T: AnkoViewBindingItem> : GenericItemAdapter<T, AnkoViewModelBindingViewHolder<T>>() {

    /**
     * ViewComponent Tag 에 대한 ViewType 정의
     *
     * key : ViewComponent Tag
     * value : viewType
     */
    private val _viewTagByTypeGroup: MutableMap<String, Int> = HashMap()

    /**
     * ViewType 에 대한 ViewComponent Tag 정의
     *
     * key : viewType
     * value : viewType
     */
    private val _viewTypeByComponentGroup: MutableMap<Int, AnkoComponent<ViewGroup>> = HashMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnkoViewModelBindingViewHolder<T> {
        val viewComponent = when(viewType) {
            HEADER_VIEW_TYPE -> header?.getViewComponent()
            FOOTER_VIEW_TYPE -> footer?.getViewComponent()
            else -> _viewTypeByComponentGroup[viewType]
        }

        val viewHolder = viewComponent
                ?.createView(AnkoContext.fromViewGroup(parent))
                ?.let { AnkoViewModelBindingViewHolder<T>(it) }

        return viewHolder ?: kotlin.run { throw NotFoundViewTypeException() }
    }

    override fun notifyItemViewTypeChanged() {
        _viewTagByTypeGroup.clear()
        _viewTypeByComponentGroup.clear()

        var viewType = VIEW_TYPE_START_INDEX
        items?.forEach {
            val viewComponent = it.getViewComponent()
            val viewComponentTag = viewComponent.getTag()

            val isNoneContainTag = !_viewTagByTypeGroup.containsKey(viewComponentTag)
            if (isNoneContainTag) {
                _viewTagByTypeGroup[viewComponentTag] = viewType
                _viewTypeByComponentGroup[viewType] = viewComponent

                ++viewType
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(position) {
        0 ->
            header?.let { HEADER_VIEW_TYPE }
                    ?: items?.get(0)?.getViewComponent()?.getTag()?.let { _viewTagByTypeGroup[it] }

        itemCount - 1 ->
            footer?.let { FOOTER_VIEW_TYPE }
                    ?: items?.run { get(size - 1) }?.getViewComponent()?.getTag()?.let { _viewTagByTypeGroup[it] }


        else ->
            items?.run {
                val findPosition = header?.let { position - 1 } ?: position
                get(findPosition)
            }?.getViewComponent()?.getTag()?.let { _viewTagByTypeGroup[it] }

    }?: super.getItemViewType(position)

}