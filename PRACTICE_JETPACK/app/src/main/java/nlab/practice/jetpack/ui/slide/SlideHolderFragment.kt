package nlab.practice.jetpack.ui.slide

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Provides
import kotlinx.android.synthetic.main.fragment_slide_holder.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.FragmentSlideHolderBinding
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * Google music
 *
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideHolderFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: SlideHolderViewModel

    lateinit var binding: FragmentSlideHolderBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSlideHolderBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    @dagger.Module
    class Module {

        @FragmentScope
        @Provides
        fun provideSlideHolderViewUsecase(fragment: Fragment): SlideHolderViewUsecase {
            return SlideHolderViewUsecase(containerViewSupplier = {fragment.fragmentContainer},
                    _miniPlayerViewSupplier = { fragment.view?.findViewById(R.id.fragmentSlideControl) })
        }
    }
}