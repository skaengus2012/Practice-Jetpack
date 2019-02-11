package nlab.practice.jetpack.ui.main

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.di.fragment.FragmentScope

/**
 * @author Doohyun
 * @since 2019. 02. 11
 */
@Module
class HolderFragmentModule {

    @FragmentScope
    @Provides
    fun provideSnackBarHelper(resourceProvider: ResourceProvider, fragment: Fragment) : SnackBarHelper {
        // bottomBar 를 가릴 수 있도록 SnackBar 크기 조정
        val height = resourceProvider.getDimensionPixelSize(R.dimen.main_bottom_navigation_height)

        return SnackBarHelper(height, resourceProvider ) {
            fragment.activity?.window?.decorView?.findViewById(R.id.layoutRoot)
        }
    }
}