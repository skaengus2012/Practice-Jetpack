package nlab.practice.jetpack.ui.common

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.recyclerview.widget.LinearLayoutManager
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.databinding.driveList
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Main 화면 Container 에 대한 UI 정의
 *
 * @author Doohyun
 * @since 2018. 12. 12
 */
class MainContainerUI(private val _viewModel: MainContainerViewModel) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
        constraintLayout {
            recyclerView {
                layoutManager = LinearLayoutManager(ctx)
                descendantFocusability = FOCUS_BLOCK_DESCENDANTS
                clipToPadding = false
                leftPadding = dip(20)
                rightPadding = dip(20)
                bottomPadding = dimen(R.dimen.container_page_padding_bottom)
            }.lparams(width = matchConstraint, height = matchConstraint) {
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
            }.driveList(
                items = _viewModel.getItems(),
                headerItem =  _viewModel.getHeader(),
                footerItem = _viewModel.getFooter()
            )
        }
    }.view
}