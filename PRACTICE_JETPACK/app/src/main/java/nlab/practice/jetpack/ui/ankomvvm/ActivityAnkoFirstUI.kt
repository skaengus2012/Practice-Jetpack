package nlab.practice.jetpack.ui.ankomvvm

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.color
import nlab.practice.jetpack.util.databinding.drive
import nlab.practice.jetpack.util.string
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.guideline
import org.jetbrains.anko.constraint.layout.matchConstraint

const val HORIZONTAL_GUIDE_LINE_ID = 1

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class ActivityAnkoFirstUI(private val viewModel: AnkoFirstViewModel) : AnkoComponent<AnkoFirstActivity> {

    override fun createView(ui: AnkoContext<AnkoFirstActivity>): View = ui.apply {
        constraintLayout {
            setBackgroundColor(color(R.color.colorAccent))

            textView {
                textColor = color(android.R.color.white)
                textSize = sp(20).toFloat()
                setTypeface(typeface, Typeface.BOLD)
            }.lparams (width = wrapContent, height = wrapContent){
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topToTop = PARENT_ID
                bottomToTop = HORIZONTAL_GUIDE_LINE_ID
            }.drive(viewModel.message) {
                text = it.get()
            }

            guideline{
                id = HORIZONTAL_GUIDE_LINE_ID
            }.lparams(width = wrapContent, height = wrapContent) {
                orientation = HORIZONTAL
                guidePercent = 0.5f
            }

            linearLayout {
                gravity = Gravity.CENTER
                orientation = LinearLayout.VERTICAL

                getTextChangeButton(ctx, R.string.anko_first_btn_change_text)
                        .lparams(width = matchParent, height = wrapContent) {
                            bottomMargin = dip(5)
                            leftMargin = dip(20)
                            rightMargin = dip(20)
                        }
                        .setOnClickListener {
                            viewModel.changeTextDelayTime(string(R.string.anko_first_message_change))
                        }

                getTextChangeButton(ctx, R.string.anko_first_btn_change_text_delay)
                        .lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(5f)
                            leftMargin = dip(20)
                            rightMargin = dip(20)
                        }
                        .setOnClickListener {
                            viewModel.changeTextDelayTime(string(R.string.anko_first_message_change_delay), 5)
                        }

            }.lparams(width = matchConstraint, height = matchConstraint) {
                startToStart = PARENT_ID
                endToEnd = PARENT_ID
                topToBottom = HORIZONTAL_GUIDE_LINE_ID
                bottomToBottom = PARENT_ID
            }
        }

    }.view

    private fun ViewManager.getTextChangeButton(ctx: Context, @StringRes labelRes: Int) : Button = button(labelRes) {
        minimumHeight = dip(40)

        backgroundColor = ctx.color(android.R.color.black)

        textColor = ctx.color(android.R.color.white)
        textSize = 15f
        setTypeface(typeface, Typeface.BOLD)
        setPadding(dip(15), dip(10), dip(15), dip(10))
    }
}