package nlab.practice.jetpack.ui.layout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class NavigationExampleUI(@ColorRes val colorRes: Int) : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
        constraintLayout {
            backgroundColor = ContextCompat.getColor(ctx, colorRes)
        }
    }.view
}