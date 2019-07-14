/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.util.di.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import dagger.android.support.AndroidSupportInjection
import nlab.practice.jetpack.util.BaseFragment
import nlab.practice.jetpack.util.component.callback.FragmentCallback
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
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

    lateinit var fragmentBindComponent: FragmentBindComponent
        private set

    @Inject
    lateinit var lifeCycleBinder: FragmentLifeCycleBinder

    @Inject
    lateinit var fragmentCallbackBinder: FragmentCallback

    private fun initializeDI() {
        activity?.application
            ?.let { it as? AppComponent.Supplier }
            ?.getAppComponent()
            ?.fragmentBindComponent()
            ?.setOwner(this)
            ?.build()
            ?.run { fragmentBindComponent = this }

        AndroidSupportInjection.inject(this)
    }

    @CallSuper
    override fun onAttach(context: Context?) {
        // 권고사항 : Dagger Reference 에서는 onAttach 시점 전, DI 를 하기를 권장
        initializeDI()

        super.onAttach(context)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_ATTACH)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifeCycleBinder.apply(FragmentLifeCycle.ON_CREATE)
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifeCycleBinder.apply(FragmentLifeCycle.ON_CREATE_VIEW)

        return onCreateBindingView(inflater, container, savedInstanceState)
    }

    /**
     * onCreateView 의 추상 객체
     *
     * onCreateView 에서 lifecycle 을 제공해야하기때문에 해당 메소드로 대체
     */
    abstract fun onCreateBindingView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

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
    override fun onBackPressed(): Boolean {
        return fragmentCallbackBinder.onBackPressedCommand?.invoke() ?: false
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        if (!isStateSaved) {
            lifeCycleBinder.apply(FragmentLifeCycle.FINISH)
        }

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DESTROY)
    }

    override fun onDetach() {
        super.onDetach()

        lifeCycleBinder.apply(FragmentLifeCycle.ON_DETACH)
        lifeCycleBinder.clear()
    }
}