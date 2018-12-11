package nlab.practice.jetpack

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * @author Doohyun
 * @since 2018. 12. 11
 */
object LeakCanaryWatcher {

    private var refWatcher: RefWatcher? = null

    fun initialize(application: Application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            refWatcher = LeakCanary.install(application)
        }
    }

    fun watch(watchedReference: Any) {
        refWatcher?.watch(watchedReference)
    }
}