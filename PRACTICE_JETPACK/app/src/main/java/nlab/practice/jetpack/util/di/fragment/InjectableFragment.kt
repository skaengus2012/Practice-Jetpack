package nlab.practice.jetpack.util.di.fragment

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import dagger.android.support.AndroidSupportInjection
import nlab.practice.jetpack.util.BaseFragment
import nlab.practice.jetpack.util.di.AppComponent
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

    private lateinit var _fragmentBindComponent: FragmentBindComponent

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initializeDI()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_ATTACH)
    }

    private fun initializeDI() {
        activity?.application
                ?.let { it as? AppComponent.Supplier }
                ?.getAppComponent()
                ?.fragmentBindComponent()
                ?.build()
                ?.run { _fragmentBindComponent = this }

        AndroidSupportInjection.inject(this)
    }

    fun getFragmentBindComponent(): FragmentBindComponent = _fragmentBindComponent

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_CREATE)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_CREATE_VIEW)

        return view
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_ACTIVITY_CREATED)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_VIEW_CREATED)
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
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DESTROY)
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DETACH)
    }
}