package nlab.practice.jetpack.ui.listadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.databinding.FragmentListAdapterExampleBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment

/**
 * ListAdapter 예제 구현을 위한 Fragment
 *
 * @author Doohyun
 */
class ListAdapterExampleFragment: InjectableFragment() {

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

        @Provides
        fun provideItemViewModelFactory(): ListAdapterExampleItemViewModelFactory {
            return ListAdapterExampleItemViewModelFactory()
        }
    }
}