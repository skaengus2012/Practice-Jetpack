package nlab.practice.jetpack

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main_holder.*
import nlab.practice.jetpack.common.BaseActivity

/**
 * Fragment 들의 Host Activity
 *
 * One-Activity 구조로 다수 Fragment 들을 사용하도록 한다.
 *
 * @author Doohyun
 */
class MainHolderActivity : BaseActivity() {

    private lateinit var _navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_holder)

        _navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, _navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return _navController.navigateUp()
    }
}
