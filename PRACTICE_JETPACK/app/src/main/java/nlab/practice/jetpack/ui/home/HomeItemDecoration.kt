package nlab.practice.jetpack.ui.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.R
import org.jetbrains.anko.dimen

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class HomeItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val _bottomMargin = context.dimen(R.dimen.home_page_item_bottom_margin)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position != 0) {
            outRect.bottom = _bottomMargin
        }
    }
}