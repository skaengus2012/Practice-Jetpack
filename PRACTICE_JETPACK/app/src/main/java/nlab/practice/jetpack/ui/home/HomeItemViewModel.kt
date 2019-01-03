package nlab.practice.jetpack.ui.home

import androidx.annotation.IdRes
import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.recyclerview.databinding.DataBindingItemViewModel

/**
 * Home 에서 사용하는 Item 정의
 *
 * @author Doohyun
 */
@AutoFactory
class HomeItemViewModel(
        private val _testMenu: TestMenu,
        @IdRes private val _navActionId: Int) : DataBindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_home_item

    @Bindable
    fun getTitleVisibleYn(): Boolean = _testMenu.cardTitle != null

    @Bindable
    fun getCardTitle(): String? = _testMenu.cardTitle

    @Bindable
    fun getTitle(): String = _testMenu.title

    fun onClick() {
        itemViewUsecaseFactory?.navigateViewUsecase()?.navigatePage(_navActionId)
    }
}