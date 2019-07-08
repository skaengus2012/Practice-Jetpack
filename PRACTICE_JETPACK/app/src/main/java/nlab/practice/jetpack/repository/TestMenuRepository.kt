/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
            title = resourceProvider.getString(R.string.test_menu_anko_first),
            cardColorRes = R.color.home_item_color_1
    )

    fun getPagingTestMenu(): TestMenu = TestMenu(
            title =  resourceProvider.getString(R.string.test_menu_paging),
            cardColorRes = R.color.home_item_color_2,
            cardTitle = resourceProvider.getString(R.string.test_menu_paging_description)
    )

    fun getListAdapterMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_listadater),
            cardColorRes = R.color.home_item_color_3,
            cardTitle = resourceProvider.getString(R.string.test_menu_listadater_description)
    )

    fun getDragDropMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_item_touch),
            cardTitle =  resourceProvider.getString(R.string.test_menu_drag_drop_description),
            cardColorRes = R.color.home_item_color_4
    )

    fun getCollapsingToolbarExMenu(): TestMenu = TestMenu(
            title = resourceProvider.getString(R.string.test_menu_collapsing_toolbar),
            cardTitle = resourceProvider.getString(R.string.test_menu_collapsing_toolbar_description),
            cardColorRes = R.color.home_item_color_5
    )

    fun getSlideUpPanelExMenus() = TestMenu(
            title = resourceProvider.getString(R.string.test_slide_up_panel_ex_title),
            cardTitle = resourceProvider.getString(R.string.test_slide_up_panel_ex_description),
            cardColorRes = R.color.home_item_color_6
    )

    fun getCenterScrollRecyclerView() = TestMenu(
            title = resourceProvider.getString(R.string.test_center_scroll_recycler_title),
            cardTitle = resourceProvider.getString(R.string.test_center_scroll_recycler_description),
            cardColorRes = R.color.home_item_color_7
    )

    fun getLandScapeModeMenu() = TestMenu(
            title = resourceProvider.getString(R.string.test_landscape_mode_title),
            cardTitle = resourceProvider.getString(R.string.test_landscape_mode_description),
            cardColorRes = R.color.home_item_color_8
    )

}