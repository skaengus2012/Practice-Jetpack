package nlab.practice.jetpack.ui.slide

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.Provides
import kotlinx.android.synthetic.main.activity_slide_up_sample.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivitySlideUpSampleBinding
import nlab.practice.jetpack.util.ViewSupplier
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleActivity : InjectableActivity(), SlidingUpPanelActivity.Owner {

    @Inject
    lateinit var viewModel: SlideUpSampleViewModel

    @Inject
    lateinit var slidingUpPanelActivity: SlidingUpPanelActivity

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val binding: ActivitySlideUpSampleBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_slide_up_sample)

        binding.viewModel = viewModel
    }

    override fun getSlidingUpPanelDelegate(): SlidingUpPanelActivity = slidingUpPanelActivity

    @dagger.Module(includes = [SlidingUpPanelActivityModule::class])
    class Module {

        @ActivityScope
        @Provides
        fun provideSlidingUpPanelLayout(activity: Activity) = ViewSupplier { activity.slidingLayout }
    }
}