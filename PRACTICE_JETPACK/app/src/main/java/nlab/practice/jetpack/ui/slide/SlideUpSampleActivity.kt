package nlab.practice.jetpack.ui.slide

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivitySlideUpSampleBinding
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: SlideUpSampleViewModel

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val binding: ActivitySlideUpSampleBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_slide_up_sample)

        binding.viewModel = viewModel
    }
}