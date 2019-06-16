package nlab.practice.jetpack.ui.centerscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_center_scroll.*
import nlab.practice.jetpack.databinding.FragmentCenterScrollBinding
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.CenteringRecyclerViewUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CenterScrollerFragment : InjectableFragment() {

    lateinit var binding: FragmentCenterScrollBinding

    @Inject
    lateinit var viewModel: CenterScrollViewModel

    override fun onCreateBindingView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCenterScrollBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @FragmentScope
        @Provides
        fun provideRecyclerViewUsecase(fragment: Fragment) = CenteringRecyclerViewUsecase { fragment.lvContents }
    }
}