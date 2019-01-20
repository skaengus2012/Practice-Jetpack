package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentPagingCountableBinding
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class CountablePagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: CountablePagingViewModel

    lateinit var binding: FragmentPagingCountableBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPagingCountableBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    @dagger.Module(includes = [
        ChildFragmentModule::class,
        PositionalPagingModule::class
    ])
    class Module
}