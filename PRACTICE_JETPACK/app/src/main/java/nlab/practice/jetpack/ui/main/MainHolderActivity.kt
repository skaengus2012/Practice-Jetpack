package nlab.practice.jetpack.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nlab.practice.jetpack.R
import nlab.practice.jetpack.databinding.ActivityMainHolderBinding
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import javax.inject.Inject

/**
 * Fragment 들의 Host Activity
 *
 * One-Activity 구조로 다수 Fragment 들을 사용하도록 한다.
 *
 * @author Doohyun
 */
class MainHolderActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: MainHolderViewModel

    private lateinit var _binding: ActivityMainHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        _binding.viewModel = viewModel
    }
}
