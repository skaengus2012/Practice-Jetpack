package nlab.practice.jetpack.ui.collapsingtoolbar

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivityCollapsingToolbarBinding
import nlab.practice.jetpack.util.di.activity.InjectableActivity

/**
 * CollapsingToolbar 에 대한 연습 화면
 *
 * @author Doohyun
 */
class CollapsingToolbarActivity : InjectableActivity() {

    lateinit var viewModel: CollapsingToolbarViewModel

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        DataBindingUtil.setContentView<ActivityCollapsingToolbarBinding>(this, R.layout.activity_collapsing_toolbar)
                .run { this.viewModel = viewModel }
    }
}