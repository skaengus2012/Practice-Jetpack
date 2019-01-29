package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
class UnboundedPagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: UnboundedPagingViewModel

    lateinit var binding: FragmentPagingBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentPagingBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    @dagger.Module(includes = [
        ChildFragmentModule::class,
        PositionalPagingModule::class
    ])
    class Module
}