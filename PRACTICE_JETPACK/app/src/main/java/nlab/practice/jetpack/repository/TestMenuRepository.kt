package nlab.practice.jetpack.repository

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.ResourceProvider

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class TestMenuRepository(private val resourceProvider: ResourceProvider) {

    fun getAnkoFirstViewMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_anko_first).toString(),
            cardColorRes = R.color.home_item_color_1
    )

    fun getPagingTestMenu(): TestMenu = TestMenu(
            title =  resourceProvider.getString(R.string.test_menu_paging).toString(),
            cardColorRes = R.color.home_item_color_2,
            cardTitle = resourceProvider.getString(R.string.test_menu_paging_description).toString()
    )

    fun getListAdapterMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_listadater).toString(),
            cardColorRes = R.color.home_item_color_3,
            cardTitle = resourceProvider.getString(R.string.test_menu_listadater_description).toString()
    )

    fun getDragDropMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_item_touch).toString(),
            cardTitle =  resourceProvider.getString(R.string.test_menu_drag_drop_description).toString(),
            cardColorRes = R.color.home_item_color_4
    )

    fun getCollapsingToolbarExMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_collapsing_toolbar).toString(),
            cardTitle = resourceProvider.getString(R.string.test_menu_collapsing_toolbar_description).toString(),
            cardColorRes = R.color.home_item_color_5
    )

    fun getSlideUpPanelExMenus() = TestMenu(
            title = resourceProvider.getString(R.string.test_slide_up_panel_ex_title).toString(),
            cardTitle = resourceProvider.getString(R.string.test_slide_up_panel_ex_description).toString(),
            cardColorRes = R.color.home_item_color_6
    )

}