package nlab.practice.jetpack.util.nav

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import javax.inject.Named

/**
 * @author Doohyun
 */
@Module
class FragmentScopeNavModule {

    @Named(NavController.Named.DEFAULT)
    @FragmentScope
    @Provides
    fun provideNavController(fragment: Fragment): NavController {
        return (fragment.activity as? AppCompatActivity)?.supportFragmentManager?.let { NavController(it, 0) }!!
    }

    @Named(NavController.Named.CHILD)
    @FragmentScope
    @Provides
    fun provideChildNavController(fragment: Fragment): NavController = NavController(fragment.childFragmentManager, 0)
}