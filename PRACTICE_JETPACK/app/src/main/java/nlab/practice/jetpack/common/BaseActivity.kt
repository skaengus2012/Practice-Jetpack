package nlab.practice.jetpack.common

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 20
 */
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var lifeCycleBinder: ActivityLifeCycleBinder

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        lifeCycleBinder.apply(ActivityLifeCycle.ON_CREATE)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_RESUME)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_PAUSE)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_STOP)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_DESTROY)
    }

    @CallSuper
    override fun finish() {
        super.finish()

        lifeCycleBinder.apply(ActivityLifeCycle.FINISH)
    }
}