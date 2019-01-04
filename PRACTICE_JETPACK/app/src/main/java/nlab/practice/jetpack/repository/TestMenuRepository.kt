package nlab.practice.jetpack.repository

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.ResourceProvider

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class TestMenuRepository(private val _resourceProvider: ResourceProvider) {

    fun getAnkoFirstViewMenu(): TestMenu = TestMenu(
            title = _resourceProvider.getString(R.string.test_menu_anko_first).toString(),
            cardColorRes = R.color.home_item_color_1
    )

    fun getPagingTestMenu(): TestMenu = TestMenu(
            title =  _resourceProvider.getString(R.string.test_menu_paging).toString(),
            cardColorRes = R.color.home_item_color_2,
            cardTitle = _resourceProvider.getString(R.string.test_menu_paging_description).toString()
    )

}