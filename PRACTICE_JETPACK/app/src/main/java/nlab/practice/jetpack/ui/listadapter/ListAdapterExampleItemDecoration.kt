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

    private val horizontalInnerSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal_inner)
    private val horizontalSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_horizontal)
    private val bottomSize = resourceProvider.getDimensionPixelSize(R.dimen.list_adapter_margin_bottom)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val column = position % ListAdapterExampleViewModel.SPAN_COUNT

        if (column == 0) {
            outRect.left = horizontalSize
            outRect.right = horizontalInnerSize
        } else {
            outRect.left = horizontalInnerSize
            outRect.right = horizontalSize
        }

        outRect.bottom = bottomSize
    }
}