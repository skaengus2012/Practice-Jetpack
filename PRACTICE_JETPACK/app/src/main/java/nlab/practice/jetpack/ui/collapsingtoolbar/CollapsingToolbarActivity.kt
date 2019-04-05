package nlab.practice.jetpack.ui.collapsingtoolbar

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivityCollapsingToolbarBinding
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * CollapsingToolbar 에 대한 연습 화면
 *
 * @author Doohyun
 */
class CollapsingToolbarActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: CollapsingToolbarViewModel

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        val binding: ActivityCollapsingToolbarBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_collapsing_toolbar)
        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {

        @ActivityScope
        @Provides
        fun getItemFactory() = CollapsingPagingItemViewModelFactory()

        @ActivityScope
        @Provides
        fun getToolbarItemVisibilityUsecase(activity: Activity) = ToolbarItemVisibilityUsecase(
                {activity.appbarLayout},
                {activity.collapsingToolbar})

    }
}