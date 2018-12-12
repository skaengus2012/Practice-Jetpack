package nlab.practice.jetpack.ui.home

import android.view.View
import android.view.ViewParent
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeHeaderUI(private val _viewModel: HomeHeaderViewModel) : AnkoComponent<ViewParent> {

    override fun createView(ui: AnkoContext<ViewParent>): View = ui.apply {
        constraintLayout {

        }

    }.view
}