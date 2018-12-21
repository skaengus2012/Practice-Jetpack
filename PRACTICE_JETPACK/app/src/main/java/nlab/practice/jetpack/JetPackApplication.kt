package nlab.practice.jetpack

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import nlab.practice.jetpack.common.di.AppComponent
import nlab.practice.jetpack.common.di.DaggerAppComponent
import javax.inject.Inject

/**
 * @author Doohyun
 */
class JetPackApplication : Application(), AppComponent.Supplier, HasActivityInjector {

    private lateinit var _appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initializeDI()
        LeakCanaryWatcher.initialize(this)
    }

    private fun initializeDI() {
        _appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        _appComponent.inject(this)
    }

    override fun getAppComponent(): AppComponent = _appComponent

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}