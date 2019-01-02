package nlab.practice.jetpack.ui.home

import android.view.View
import android.view.ViewParent
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.navigation.Navigation
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.databinding.drive
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class HomeItemUI(private val _viewModel: HomeItemDeViewModel): AnkoComponent<ViewParent> {

    override fun createView(ui: AnkoContext<ViewParent>): View = ui.apply {
        constraintLayout {
            lparams(width = matchParent, height = wrapContent)

            themedTextView(R.style.MainContainerSubTitle) {
                id = R.id.tv_home_title
            }.lparams(width = wrapContent, height = wrapContent) {
                topToTop = PARENT_ID
                startToStart = PARENT_ID
                bottomToTop = R.id.card_home
                bottomMargin = dip(12)
            }.drive(_viewModel) {
                text = it.testMenu.cardTitle
                visibility = when(_viewModel.isCardTitleVisible()) {
                    true -> View.VISIBLE
                    else -> View.GONE
                }
            }

            cardView {
                id = R.id.card_home
                radius = dip(4).toFloat()

                constraintLayout {
                    lparams(width = matchParent, height = dip(154))

                    imageView {

                        backgroundColorResource = R.color.colorAccent
                    }.lparams(width = matchParent, height = matchParent)

                    textView {
                        textSize = 19f
                        textColorResource = R.color.white
                        singleLine = true
                    }.lparams(width = wrapContent, height = wrapContent) {
                        leftMargin = dip(14)
                        bottomMargin = dip(21.5f)
                        startToStart = PARENT_ID
                        bottomToBottom = PARENT_ID
                    }.drive(_viewModel) {
                        text = it.testMenu.title
                    }

                }

            }.lparams(width = matchParent, height = wrapContent) {
                topToBottom = R.id.tv_home_title
                bottomToBottom = PARENT_ID
            }.drive(_viewModel) {
                viewModel
                ->
                setOnClickListener { Navigation.findNavController(this).navigate(viewModel.naviActionId) }
            }
        }
    }.view

}