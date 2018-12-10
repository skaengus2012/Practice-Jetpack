package nlab.practice.jetpack.util

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Navigation 관련 UI 함수 정의
 *
 * @author Doohyun
 */

fun BottomNavigationView.setupWithNavController(navController: NavController) {
    setOnNavigationItemSelectedListener {

    }
}

private fun onNavDestinationSelected(menuItem: MenuItem, navController: NavController, isPopUp: Boolean) : Boolean {
    val builder = NavOptions.Builder().setLaunchSingleTop(true)

    // TODO folk
    if (isPopUp) {
        builder.
    }
}

private fun findStartDestination(graph: NavGraph) : NavDestination {

}
