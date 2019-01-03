package nlab.practice.jetpack.util.di.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.BaseFragment
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifeCycleBinder
import javax.inject.Inject

/**
 * Injection 을 할 수 있는 Fragment
 *
 * DI 를 이용하는 Fragment 는 모두 이 클래스를 상속받아야함
 *
 * @author Doohyun
 * @since 2018. 12. 11
 */
abstract class InjectableFragment : BaseFragment() {

    @Inject
    lateinit var lifeCycleBinder: FragmentLifeCycleBinder

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var _fragmentBindComponent: FragmentBindComponent

    private fun initializeDI(activity: Activity) {
        (activity as? InjectableActivity)?.run {
            (application as? AppComponent.Supplier)
                    ?.getAppComponent()
                    ?.fragmentBindComponent()
                    ?.setActivityLifeCycleBinder(lifeCycleBinder)
                    ?.build()
                    ?.run { _fragmentBindComponent = this }
        }

        AndroidSupportInjection.inject(this)
    }

    fun getFragmentBindComponent(): FragmentBindComponent = _fragmentBindComponent

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 먼저 수행을 해야, lifeCycleBinder 가 onCreateView 를 detect 할 수 있음
        activity?.run { initializeDI(this) }

        val view = onCreateBindingView(inflater, container, savedInstanceState)
        lifeCycleBinder.apply(FragmentLifeCycle.ON_CREATE_VIEW)
        return view
    }

    /**
     * onCreateView 의 추상 객체
     *
     * onCreateView 에서 lifecycle 을 제공해야하기때문에 해당 메소드로 대체
     */
    abstract fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_ACTIVITY_CREATED)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_RESUME)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_PAUSE)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_STOP)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DESTROY_VIEW)
        compositeDisposable.clear()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DESTROY)
    }
}