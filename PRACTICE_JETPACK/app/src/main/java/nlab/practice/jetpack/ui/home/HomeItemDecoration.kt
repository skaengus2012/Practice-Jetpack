package nlab.practice.jetpack.ui.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider

/**
 * Home 의 전체 데코레이션 정의
 *
 * @author Doohyun
 */
class HomeItemDecoration(private val _resourceProvider: ResourceProvider): RecyclerView.ItemDecoration()  {

    private val _bottomMargin : Int by lazy { _resourceProvider.getDimensionPixelSize(R.dimen.home_page_item_bottom_margin) }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.bottom = _bottomMargin
        }
    }
}