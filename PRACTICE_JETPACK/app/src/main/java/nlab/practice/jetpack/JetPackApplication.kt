package nlab.practice.jetpack

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.di.DaggerAppComponent
import nlab.practice.jetpack.util.di.activity.ActivityInjector

/**
 * @author Doohyun
 */
class JetPackApplication : Application(), AppComponent.Supplier, HasActivityInjector {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initializeDI()
        LeakCanaryWatcher.initialize(this)
    }

    private fun initializeDI() {
        _appComponent = DaggerAppComponent.builder()
                .setApplication(this)
                .build()
    }

    override fun getAppComponent(): AppComponent = _appComponent

    override fun activityInjector(): AndroidInjector<Activity> = ActivityInjector
}