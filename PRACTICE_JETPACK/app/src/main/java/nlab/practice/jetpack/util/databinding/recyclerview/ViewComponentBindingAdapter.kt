package nlab.practice.jetpack.util.databinding.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
class ViewComponentBindingAdapter<T: ViewComponentBindingItem>(items: List<T>)
    : RecyclerView.Adapter<ViewComponentBindingHolder>() {

    val items: MutableList<T> = ArrayList(items)
    var header: T? = null
    var footer: T? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewComponentBindingHolder = when(viewType) {
                HEADER_VIEW_TYPE -> header?.getViewComponent()
                FOOTER_VIEW_TYPE -> footer?.getViewComponent()
                else -> _viewTypeByComponentGroup[viewType]
            }
            ?.createView(AnkoContext.create(parent.context, parent))
            ?.let { ViewComponentBindingHolder(it) }
            ?: kotlin.run { throw EmptyViewTypeException() }


    /**
     * ViewType 관계 그룹 초기화
     *
     * NOTE : Item 항목이 변경될 때, 해당 이벤트를 초기화해줘야함
     */
    fun initializeViewTypeMapper() {
        _viewTagByTypeGroup.clear()
        _viewTypeByComponentGroup.clear()

        var viewType = VIEW_TYPE_START_INDEX
        for (itemViewModel in items) {
            val viewComponent = itemViewModel.getViewComponent()
            val viewComponentTag = viewComponent.getTag()

            val isNoneContainTag = !_viewTagByTypeGroup.containsKey(viewComponentTag)
            if (isNoneContainTag) {
                _viewTagByTypeGroup[viewComponentTag] = viewType
                _viewTypeByComponentGroup[viewType] = viewComponent

                ++viewType
            }
        }
    }

    override fun getItemCount(): Int {
        var itemSize = items.size

        // header & footer 의 사이즈를 고려해서 처리
        header?.run { ++itemSize }
        footer?.run { ++itemSize }

        return itemSize
    }

    override fun onBindViewHolder(holder: ViewComponentBindingHolder, position: Int) {
        items[position].run { holder.onBind(this) }
    }

    override fun getItemViewType(position: Int): Int = when(position) {
        0 -> {
            // 0 일 경우
            // HEADER 가 존재한다면, headerType 출력
            // HEADER 가 존재하지 않는다면, 아이템의 뷰타입을 출력
            header?.let { HEADER_VIEW_TYPE }
                    ?: items[0].getViewComponent().getTag().let { _viewTagByTypeGroup[it]!! }
        }

        itemCount - 1 -> {
            // 마지막 데이터일 경우
            // FOOTER 가 존재한다면, footerType 출력
            // FOOTER 가 존재하지 않는다면, 아이템 뷰타입을 출력

            val itemLastIndex = items.size - 1

            footer?.let { FOOTER_VIEW_TYPE }
                    ?: items[itemLastIndex].getViewComponent().getTag().let { _viewTagByTypeGroup[it]!! }
        }

        else -> {
            // HEADER 가 존재할 때만 한칸 밀어 조회한다.

            val findPosition = header?.let { position + 1 } ?: position

            items[findPosition].getViewComponent().getTag().let { _viewTagByTypeGroup[it]!! }
        }
    }
}