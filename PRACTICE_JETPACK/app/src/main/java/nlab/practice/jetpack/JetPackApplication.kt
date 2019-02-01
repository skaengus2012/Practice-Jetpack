package nlab.practice.jetpack

import android.app.Activity
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.di.DaggerAppComponent
import nlab.practice.jetpack.util.di.activity.ActivityInjector
import timber.log.Timber

private const val TAG = "JetPackApplication"

/**
 * @author Doohyun
 */
class JetPackApplication : MultiDexApplication(), AppComponent.Supplier, HasActivityInjector {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initializeDI()
        initializeRxErrorHandler()
        LeakCanaryWatcher.initialize(this)
    }

    private fun initializeDI() {
        _appComponent = DaggerAppComponent.builder()
                .setApplication(this)
                .build()
    }

    private fun initializeRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            Timber.tag(TAG).e(it, "initRxJavaErrorHandler ${it.localizedMessage}")
        }
    }

    override fun getAppComponent(): AppComponent = _appComponent

    override fun activityInjector(): AndroidInjector<Activity> = ActivityInjector
}