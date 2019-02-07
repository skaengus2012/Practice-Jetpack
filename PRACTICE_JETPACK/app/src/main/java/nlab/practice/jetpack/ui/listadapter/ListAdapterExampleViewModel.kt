package nlab.practice.jetpack.ui.listadapter

import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 02. 07
 */
class ListAdapterExampleViewModel @Inject constructor(
        private val _layoutManagerFactory: LayoutManagerFactory,
        private val _listAdapterItemFactory: ListAdapterExampleItemViewModelFactory) {


    private fun createLayoutManager() {

    }
}