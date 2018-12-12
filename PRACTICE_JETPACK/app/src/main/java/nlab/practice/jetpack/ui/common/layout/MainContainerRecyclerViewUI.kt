package nlab.practice.jetpack.ui.common.layout

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.recyclerview.widget.LinearLayoutManager
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.common.viewmodel.RecyclerViewUIViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * RecyclerView 만 가지고 있는 UI 정의
 *
 * @author Doohyun
 * @since 2018. 12. 12
 */
class MainContainerRecyclerViewUI(private val _viewModel: RecyclerViewUIViewModel) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
        constraintLayout {
            recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                descendantFocusability = FOCUS_BLOCK_DESCENDANTS
                clipToPadding = false
                bottomPadding = dimen(R.dimen.container_page_padding_bottom)
            }.lparams(width = matchConstraint, height = matchConstraint) {
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }
        }
    }.view
}