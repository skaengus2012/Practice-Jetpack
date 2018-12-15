package nlab.practice.jetpack

import android.app.Application
import nlab.practice.jetpack.di.component.AppComponent
import nlab.practice.jetpack.di.component.DaggerAppComponent

/**
 * @author Doohyun
 */
class JetPackApplication : Application(), AppComponent.Supplier {

    private lateinit var _appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()

        LeakCanaryWatcher.initialize(this)
    }

    override fun getAppComponent(): AppComponent = _appComponent
}