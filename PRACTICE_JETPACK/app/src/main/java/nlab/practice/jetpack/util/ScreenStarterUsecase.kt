package nlab.practice.jetpack.util

import android.content.Context
import android.content.Intent
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
interface ScreenStarterUsecase {
    fun startAnkoFistActivity()
}

class ScreenStarterUsecaseImpl(private val _context: Context) : ScreenStarterUsecase {
    override fun startAnkoFistActivity() {
        _context.startActivity(Intent(_context, AnkoFirstActivity::class.java))
    }
}