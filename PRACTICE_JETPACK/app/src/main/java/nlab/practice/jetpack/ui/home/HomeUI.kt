package nlab.practice.jetpack.ui.home

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HomeUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = ui.apply {
        constraintLayout {

            button {
                setOnClickListener {
                //    Navigation.findNavController(it).navigate(R.id.navigateTwoFragment)
                }
            }.lparams(width = wrapContent, height = wrapContent)
        }
    }.view
}