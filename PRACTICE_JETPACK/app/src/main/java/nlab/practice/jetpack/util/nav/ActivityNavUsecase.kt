package nlab.practice.jetpack.util.nav

import android.content.Context
import android.content.Intent
import nlab.practice.jetpack.ui.collapsingtoolbar.CollapsingToolbarActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
class ActivityNavUsecase (private val _context: Context) {

    fun startAnkoFistActivity() = _context.startActivity(Intent(_context, AnkoFirstActivity::class.java))

    fun startCollapsingToolbarActivity() = _context.startActivity(Intent(_context, CollapsingToolbarActivity::class.java))

}