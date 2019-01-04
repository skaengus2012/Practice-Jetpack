package nlab.practice.jetpack.util

import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author Doohyun
 */
@BindingMethods(value = [
    BindingMethod(type = BottomNavigationView::class, attribute = "onNavigationItemSelected", method = "setOnNavigationItemSelectedListener"),
    BindingMethod(type = BottomNavigationView::class, attribute = "onNavigationItemReSelected", method = "setOnNavigationItemReselectedListener")
])
class BottomNavViewBindingAdapter