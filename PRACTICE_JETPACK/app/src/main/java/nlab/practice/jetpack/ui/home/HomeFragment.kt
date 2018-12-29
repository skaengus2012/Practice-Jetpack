package nlab.practice.jetpack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import nlab.practice.jetpack.common.BaseFragment
import nlab.practice.jetpack.ui.common.MainContainerUI
import nlab.practice.jetpack.util.fromViewGroup
import org.jetbrains.anko.AnkoContext

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HomeFragment : BaseFragment() {

    private lateinit var _viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        return container?.let { MainContainerUI(_viewModel).createView(AnkoContext.fromViewGroup(it)) }
    }

    override fun onResume() {
        super.onResume()

        _viewModel.startHeaderTimer()
    }

    override fun onStop() {
        super.onStop()

        _viewModel.stopHeaderTimer()
    }
}