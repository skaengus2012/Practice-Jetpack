package nlab.practice.jetpack.repository

import android.content.Context
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.TestMenu
import nlab.practice.jetpack.util.string

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
class TestMenuRepository(private val _context: Context) {

    fun getAnkoFirstViewMenu(): TestMenu = TestMenu(
            title = _context.string(R.string.test_menu_anko_first)
    )

    fun getPagingTestMenu(): TestMenu = TestMenu(
            title =  _context.string(R.string.test_menu_paging),
            cardTitle = _context.string(R.string.test_menu_paging_description)
    )

}