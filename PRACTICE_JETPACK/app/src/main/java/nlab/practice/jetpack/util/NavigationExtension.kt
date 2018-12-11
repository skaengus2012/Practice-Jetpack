package nlab.practice.jetpack.util

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

private const val DEPRECATED_NAVIGATION_MESSAGE =
        "Once the NavigationUI is formally released, it will be removed Replace with Google API"

/**
 * Navigation 관련 UI 함수 정의
 *
 * @author Doohyun
 */

@Deprecated(DEPRECATED_NAVIGATION_MESSAGE)
fun BottomNavigationView.setupWithNavController(navController: NavController) {
    setOnNavigationItemSelectedListener { onNavDestinationSelected(it, navController, true) }

    val weakRef = WeakReference(this)
    navController.addOnDestinationChangedListener (object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
            val listener: NavController.OnDestinationChangedListener = this

            weakRef.get()?.run {
                (0 until menu.size())
                        .find {
                            val menuItem = menu.getItem(it)

                             matchDestinations(destination, menuItem.itemId)
                        }
                        ?.run {
                            menu.getItem(this).isChecked = true
                        }


            }?.run {
                controller.removeOnDestinationChangedListener(listener)
            }
        }
    })
}

/**
 * NavigationUI::onNavDestinationSelected 에서 애니메이션이 제거된 기능 정의
 *
 * -> 현재 Alpha 라 그런지 애니메이션 코드가 내부에 고정되어있고, 이를 추출할 방법이 있어보이진 않음
 * -> 추 후 이 기능은 정식 버전이 나올 때 제대로 처리할 것
 */
@Deprecated(DEPRECATED_NAVIGATION_MESSAGE)
private fun onNavDestinationSelected(menuItem: MenuItem, navController: NavController, isPopUp: Boolean) : Boolean {
    val builder = NavOptions.Builder().setLaunchSingleTop(true)

    if (isPopUp) {
        findStartDestination(navController.graph)?.run { builder.setPopUpTo(id, false) }
    }

    val options: NavOptions = builder.build()

    return try {
        navController.navigate(menuItem.itemId, null, options)
        true
    } catch (e: IllegalArgumentException) {
        false
    }
}

@Deprecated(DEPRECATED_NAVIGATION_MESSAGE)
private fun findStartDestination(graph: NavGraph) : NavDestination? {
    var startDestination : NavDestination? = graph
    while (startDestination is NavGraph) {
        val parent: NavGraph = startDestination
        startDestination = parent.findNode(parent.startDestination)
    }

    return startDestination
}

@Deprecated(DEPRECATED_NAVIGATION_MESSAGE)
private fun matchDestinations(destination: NavDestination, @IdRes destinationId: Int) : Boolean = when (destination.id) {

    destinationId -> true

    else -> {
        var parentDestination: NavDestination? = destination.parent
        while (parentDestination != null) {
            if (parentDestination.id == destinationId) {
                true
            }

            parentDestination = parentDestination.parent
        }

        false
    }
}
