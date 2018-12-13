package nlab.practice.jetpack.ui.home

import android.view.View
import android.view.ViewParent
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.Navigation
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.databinding.drive
import nlab.practice.jetpack.util.string
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeHeaderUI(private val _viewModel: HomeHeaderViewModel) : AnkoComponent<ViewParent> {

    override fun createView(ui: AnkoContext<ViewParent>): View = ui.apply {
        constraintLayout {
            lparams(width = matchParent, height = dimen(R.dimen.container_page_header_height))

            themedTextView(R.style.MainContainerTitle) {
                id = R.id.main_container_title
                text = string(R.string.home_title)
            }.lparams(width = wrapContent, height = wrapContent) {
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                startToStart = PARENT_ID
                endToStart = R.id.main_container_sub_title
            }.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.nav_introduce_fragment)
            }

            textView {
                id = R.id.main_container_sub_title
                textSize = 14f
                textColorResource = R.color.white_opa40
            }.lparams(width = wrapContent, height = wrapContent) {
                topToTop = R.id.main_container_title
                bottomToBottom = R.id.main_container_title
                startToEnd = R.id.main_container_title

                verticalBias = 0.7f
                leftMargin = dip(6)
            }.drive(_viewModel, BR.currentTimeString) {
                text = _viewModel.currentTimeString
            }
        }

    }.view
}