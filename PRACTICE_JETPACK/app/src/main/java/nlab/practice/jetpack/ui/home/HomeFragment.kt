package nlab.practice.jetpack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentHomeBinding
import nlab.practice.jetpack.ui.main.ContainerFragment
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.nav.ChildNavController
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HomeFragment : InjectableFragment(), ContainerFragment {

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var navController: ChildNavController

    lateinit var binding: FragmentHomeBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    override fun getChildNavController(): ChildNavController = navController
}