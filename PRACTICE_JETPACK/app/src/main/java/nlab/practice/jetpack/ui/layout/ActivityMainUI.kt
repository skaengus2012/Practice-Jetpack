package nlab.practice.jetpack.ui.layout

import android.graphics.Typeface
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import nlab.practice.jetpack.MainActivity
import nlab.practice.jetpack.R
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * layout MainActivity
 *
 * @author Doohyun
 * @since 2018. 10. 19
 */
class ActivityMainUI(private val _text : ObservableField<String>) : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View = ui.apply {
        constraintLayout {
            setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorAccent))

            textView {
                textColor = ContextCompat.getColor(ctx, android.R.color.white)
                textSize = 20f  // anko 에서 다음 식은 20sp 임
                setTypeface(typeface, Typeface.BOLD)
            }.lparams(width = wrapContent, height = wrapContent) {
                startToStart = PARENT_ID
                topToTop = PARENT_ID
                bottomToBottom = PARENT_ID
                endToEnd = PARENT_ID
            }
        }

    }.view

}
