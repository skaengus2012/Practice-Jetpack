package nlab.practice.jetpack.util

import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.Navigation
import nlab.practice.jetpack.util.di.itemview.ItemViewScope
import javax.inject.Inject

/**
 * Navigation 역할을 수행하는 View 관련 유스케이스 정의
 *
 * @author Doohyun
 */
@ItemViewScope
class NavigateViewUsecase @Inject constructor(private val _view: View) {

    fun navigatePage(@IdRes navActionId: Int) {
        Navigation.findNavController(_view).navigate(navActionId)
    }
}