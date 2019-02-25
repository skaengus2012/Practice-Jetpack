package nlab.practice.jetpack.ui.itemtouch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.Provides
import nlab.practice.jetpack.databinding.FragmentItemTouchHelperBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * Drag N Drop 을 수행하는 화면 정의
 *
 * @author Doohyun
 */
class ItemTouchHelperFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: ItemTouchHelperViewModel

    lateinit var binding: FragmentItemTouchHelperBinding

    override fun onCreateBindingView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentItemTouchHelperBinding.inflate(inflater, container, false)
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
        fun provideItemFactory(): ItemTouchHelperItemViewModelFactory = ItemTouchHelperItemViewModelFactory()

    }
}