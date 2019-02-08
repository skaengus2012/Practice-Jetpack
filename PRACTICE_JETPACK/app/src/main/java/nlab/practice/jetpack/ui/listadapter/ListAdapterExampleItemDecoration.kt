package nlab.practice.jetpack.ui.listadapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import javax.inject.Inject

/**
 * @author Doohyun
 */
class ListAdapterExampleItemDecoration @Inject constructor(
        resourceProvider: ResourceProvider): RecyclerView.ItemDecoration() {

    private val _horizontalInnerSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal_inner)
    private val _horizontalSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal)
    private val _bottomSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_bottom)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val column = position % ListAdapterExampleViewModel.SPAN_COUNT

        if (column == 0) {
            outRect.left = _horizontalSize
            outRect.right = _horizontalInnerSize
        } else {
            outRect.left = _horizontalInnerSize
            outRect.right = _horizontalSize
        }

        outRect.bottom = _bottomSize
    }
}