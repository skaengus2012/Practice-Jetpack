package nlab.practice.jetpack.ui.listadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_list_adapter_example.*
import nlab.practice.jetpack.databinding.FragmentListAdapterExampleBinding
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * ListAdapter 예제 구현을 위한 Fragment
 *
 * @author Doohyun
 */
class ListAdapterExampleFragment: InjectableFragment() {

    @Inject
    lateinit var viewModel: ListAdapterExampleViewModel

    lateinit var binding: FragmentListAdapterExampleBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentListAdapterExampleBinding.inflate(inflater, container, false)
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
        fun provideItemViewModelFactory(): ListAdapterExampleItemViewModelFactory {
            return ListAdapterExampleItemViewModelFactory()
        }

        @FragmentScope
        @Provides
        fun provideSelectionTrackerUsecase(fragment: Fragment) : SelectionTrackerUsecase = SelectionTrackerUsecase {
            fragment.lvContents
        }
    }
}