package nlab.practice.jetpack.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_history.*
import nlab.practice.jetpack.databinding.FragmentHistoryBinding
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class HistoryFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: HistoryViewModel

    lateinit var binding: FragmentHistoryBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentHistoryBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @Provides
        fun provideRecyclerViewUsecase(fragment: HistoryFragment): RecyclerViewUsecase = RecyclerViewUsecase {
            fragment.lvContents
        }

        @Provides
        fun provideHistoryItemViewFactory(
                resourceProvider: ResourceProvider): HistoryItemViewModelFactory = HistoryItemViewModelFactory{

            resourceProvider
        }

    }
}