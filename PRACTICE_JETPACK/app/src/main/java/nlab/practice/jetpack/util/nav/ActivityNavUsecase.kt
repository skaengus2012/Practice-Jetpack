package nlab.practice.jetpack.util.nav

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * @author Doohyun
 */
interface ActivityNavUsecase {
    fun startActivity(intent: Intent)
}

class DefaultActivityNavUsecase(private val _context: Context) : ActivityNavUsecase {
    override fun startActivity(intent: Intent) = _context.startActivity(intent)
}

class FragmentOwnerActivityNavUsecase(private val _fragment: Fragment): ActivityNavUsecase {
    override fun startActivity(intent: Intent) = _fragment.startActivity(intent)
}

inline fun <reified T: Activity> ActivityNavUsecase.startActivity(intentProvider: IntentProvider) {
    intentProvider.createActivityIntent(T::class.java).run { startActivity(this) }
}