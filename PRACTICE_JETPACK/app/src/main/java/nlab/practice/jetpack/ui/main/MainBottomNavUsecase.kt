package nlab.practice.jetpack.ui.main

import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.introduce.IntroduceFragment
import nlab.practice.jetpack.util.nav.fragmentTag

/**
 * Main 에서 표시된 BottomNavigationView 의 컨트롤러
 *
 * @author Doohyun
 */
class MainBottomNavUsecase(
        private val _navController: MainNavController,
        bottomNavViewProvider: () -> BottomNavigationView)
    : BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private val _bottomNavigationView: BottomNavigationView by lazy(bottomNavViewProvider)

    fun initialize() {
        _bottomNavigationView.setOnNavigationItemSelectedListener(this)
        navFirstPage()

        _bottomNavigationView.setOnNavigationItemReselectedListener(this)
    }

    fun navFirstPage() {
        _bottomNavigationView.selectedItemId = R.id.menu_home
    }

    @IdRes
    fun getSelectedItemId(): Int =_bottomNavigationView.selectedItemId

    override fun onNavigationItemSelected(updateMenu: MenuItem): Boolean = when (updateMenu.itemId) {
        R.id.menu_home -> {
            _navController.replacePrimaryFragment(HomeFragment::class.fragmentTag()) { HomeFragment() }
            true
        }
        R.id.menu_setting ->  {
            _navController.replacePrimaryFragment(IntroduceFragment::class.fragmentTag()) { IntroduceFragment() }
            true
        }

        else -> false
    }

    override fun onNavigationItemReselected(updateMenu: MenuItem) {
        _navController.getCurrentContainerFragment()?.run {
            val result = onBottomNavReselected()
            if (!result) {
                getChildNavController().clearFragments()
            }
        }
    }
}