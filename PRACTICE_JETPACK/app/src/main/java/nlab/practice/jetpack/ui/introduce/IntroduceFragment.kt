package nlab.practice.jetpack.ui.introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.layout.NavigationExampleUI
import nlab.practice.jetpack.util.fromViewGroup
import org.jetbrains.anko.AnkoContext

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class IntroduceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = container?.let { NavigationExampleUI(R.color.ThreeBackGround).createView(AnkoContext.fromViewGroup(it)) }

        return view
    }
}