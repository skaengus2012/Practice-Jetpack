package nlab.practice.jetpack.ui.slide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentSlidingMainBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * @author Doohyun
 */
class SlidingMainFragment : InjectableFragment() {

    lateinit var binding: FragmentSlidingMainBinding

    @Inject
    lateinit var viewModel: SlidingMainViewModel

    override fun onCreateBindingView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSlidingMainBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }
}