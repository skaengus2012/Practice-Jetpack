package nlab.practice.jetpack.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.navigation.Navigation
import nlab.practice.jetpack.di.component.ViewModelInjectComponent
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.recyclerview.anko.AnkoViewBindingItem
import org.jetbrains.anko.AnkoComponent

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class HomeItemViewModel(
        injector: ViewModelInjectComponent,
        val testMenu: TestMenu,
        @IdRes val naviActionId: Int): AnkoViewBindingItem() {

    private val _viewComponent = HomeItemUI(this)

    init {
        injector.inject(this)
    }

    fun isCardTitleVisible(): Boolean = testMenu.cardTitle != null

    fun onClickCardView(): (View) -> Unit = { Navigation.findNavController(it).navigate(naviActionId) }

    override fun getViewComponent(): AnkoComponent<ViewGroup> = _viewComponent

}