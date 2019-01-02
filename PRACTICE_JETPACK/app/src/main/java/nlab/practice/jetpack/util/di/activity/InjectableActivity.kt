package nlab.practice.jetpack.util.di.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.BaseActivity
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.di.fragment.FragmentInjector
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * Injection 을 할 수 있는 Activity
 *
 * DI 를 이용하는 Activity 는 모두 이 클래스를 상속받아야함
 *
 * @author Doohyun
 * @since 2018. 12. 20
 */
abstract class InjectableActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var lifeCycleBinder: ActivityLifeCycleBinder

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var _activityBindComponent: ActivityBindComponent

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDI()

        lifeCycleBinder.apply(ActivityLifeCycle.ON_CREATE)
    }

    private fun initializeDI() {
        _activityBindComponent = (application as AppComponent.Supplier).getAppComponent()
                .activityBindComponent()
                .build()

        AndroidInjection.inject(this)
    }

    fun getActivityBindComponent(): ActivityBindComponent = _activityBindComponent

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
        compositeDisposable.clear()
    }

    @CallSuper
    override fun finish() {
        super.finish()

        lifeCycleBinder.apply(ActivityLifeCycle.FINISH)
    }

    final override fun supportFragmentInjector(): AndroidInjector<Fragment> = FragmentInjector
}