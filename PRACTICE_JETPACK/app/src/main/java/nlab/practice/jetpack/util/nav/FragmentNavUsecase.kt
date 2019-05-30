package nlab.practice.jetpack.util.nav

import nlab.practice.jetpack.ui.itemtouch.ItemTouchHelperFragment
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleFragment
import nlab.practice.jetpack.ui.paging.CountablePagingFragment
import nlab.practice.jetpack.ui.paging.UnboundedPagingFragment
import nlab.practice.jetpack.util.lazyPublic

/**
 * @author Doohyun
 */
class FragmentNavUsecase(navSupplier: () -> ChildNavController?) {

    private val navController: ChildNavController? by lazyPublic(navSupplier)

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun clearFragments(): Boolean = navController?.clearFragments() ?: true

    fun hasChild(): Boolean = navController?.hasChild() ?: false

    fun navCountablePaging() {
        navController?.addFragment(CountablePagingFragment::class.fragmentTag()) { CountablePagingFragment() }
    }

    fun navUnboundedPaging() {
        navController?.addFragment(UnboundedPagingFragment::class.fragmentTag()) { UnboundedPagingFragment() }
    }

    fun navListAdapterExample() {
        navController?.addFragment(ListAdapterExampleFragment::class.fragmentTag()) { ListAdapterExampleFragment() }
    }

    fun navDragDrop() {
        navController?.addFragment(ItemTouchHelperFragment::class.fragmentTag()) { ItemTouchHelperFragment()}
    }
}