package nlab.practice.jetpack.util.nav

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class IntentProvider(private val context: Context) {

    fun <T: Activity> createActivityIntent(clazz: Class<T>) = Intent(context, clazz)

}