package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class PagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: PagingViewModel

    lateinit var binding: FragmentPagingBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPagingBinding.inflate(inflater, container, false)

        return binding.root
    }
}