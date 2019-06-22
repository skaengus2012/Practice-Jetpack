package nlab.practice.jetpack.ui.home

import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * Home 에서 사용하는 Item 정의
 *
 * @author Doohyun
 */
@AutoFactory
class HomeItemViewModel(
        private val testMenu: TestMenu,
        private val navigateAction: () -> Unit) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_home_item

    @Bindable
    fun getTitleVisibleYn(): Boolean = testMenu.cardTitle != null

    @Bindable
    fun getCardTitle(): String? = testMenu.cardTitle

    @Bindable
    fun getCardColor(): Int = testMenu.cardColorRes

    @Bindable
    fun getTitle(): String = testMenu.title

    fun onClick() {
        navigateAction()
    }
}