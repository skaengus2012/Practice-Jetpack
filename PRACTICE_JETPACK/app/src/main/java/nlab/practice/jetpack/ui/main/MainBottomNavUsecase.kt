package nlab.practice.jetpack.ui.main

import android.view.MenuItem
import androidx.annotation.IdRes
import com.google.android.material.bottomnavigation.BottomNavigationView
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.home.HomeFragment
import nlab.practice.jetpack.ui.introduce.IntroduceFragment

/**
 * Main 에서 표시된 BottomNavigationView 의 컨트롤러
 *
 * @author Doohyun
 */
class MainBottomNavUsecase(private val _navController: MainNavController, bottomNavViewProvider: () -> BottomNavigationView):
        BottomNavigationView.OnNavigationItemSelectedListener {

    private val _bottomNavigationView: BottomNavigationView by lazy(bottomNavViewProvider)

    fun initialize() {
        _bottomNavigationView.setOnNavigationItemSelectedListener(this)
        navHome()
    }

    @IdRes
    fun getSelectedItemId(): Int =_bottomNavigationView.selectedItemId

    fun navHome() {
        _bottomNavigationView.selectedItemId = R.id.menu_home
    }

    override fun onNavigationItemSelected(updateMenu: MenuItem): Boolean =  when (updateMenu.itemId) {
        R.id.menu_home -> {
            _navController.replacePrimaryFragment(HomeFragment::class.java.name) { HomeFragment() }
            true
        }
        R.id.menu_setting ->  {
            _navController.replacePrimaryFragment(IntroduceFragment::class.java.name) { IntroduceFragment() }
            true
        }

        else -> false
    }
}