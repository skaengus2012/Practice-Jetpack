package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_paging.*
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class CountablePagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: CountablePagingViewModel

    lateinit var binding: FragmentPagingBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPagingBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    @dagger.Module(includes = [
        ChildFragmentModule::class,
        PositionalPagingModule::class
    ])
    class Module {
        @Provides
        fun provideRecyclerViewUsecase(fragment: Fragment): RecyclerViewUsecase = RecyclerViewUsecase {
            fragment.lvContents
        }
    }
}