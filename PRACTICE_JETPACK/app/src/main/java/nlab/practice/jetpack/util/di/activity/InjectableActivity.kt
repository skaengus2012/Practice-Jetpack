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

package nlab.practice.jetpack.util.di.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import dagger.android.AndroidInjection

import nlab.practice.jetpack.util.BaseActivity
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import javax.inject.Inject

/**
 * Injection 을 할 수 있는 Activity
 *
 * DI 를 이용하는 Activity 는 모두 이 클래스를 상속받아야함
 *
 * @author Doohyun
 * @since 2018. 12. 20
 */
abstract class InjectableActivity : BaseActivity() {

    lateinit var activityBindComponent: ActivityBindComponent
        private set

    @Inject
    lateinit var lifeCycleBinder: ActivityLifecycleBinder

    @Inject
    lateinit var activityCallbackBinder: ActivityCallback

    final override fun onCreate(savedInstanceState: Bundle?) {
        // 권고사항 : Dagger Reference 에서는 onCreate 시점 전, DI 를 하기를 권장
        initializeDI()
        super.onCreate(savedInstanceState)

        onCreateBinding(savedInstanceState)

        lifeCycleBinder.apply(ActivityLifecycle.ON_CREATE)
    }

    abstract fun onCreateBinding(savedInstanceState: Bundle?)

    private fun initializeDI() {
        activityBindComponent = (application as AppComponent.Supplier).getAppComponent()
            .activityBindComponent()
            .setActivity(this)
            .build()

        AndroidInjection.inject(this)
    }

    fun bindInjection() {
        activityBindComponent.activityInjector().maybeInject(this)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        lifeCycleBinder.apply(ActivityLifecycle.ON_START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        lifeCycleBinder.apply(ActivityLifecycle.ON_RESUME)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()

        lifeCycleBinder.apply(ActivityLifecycle.ON_PAUSE)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()

        lifeCycleBinder.apply(ActivityLifecycle.ON_STOP)
    }

    @CallSuper
    override fun finish() {
        super.finish()

        lifeCycleBinder.apply(ActivityLifecycle.FINISH)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        with(lifeCycleBinder) {
            apply(ActivityLifecycle.ON_DESTROY)
            clear()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        activityCallbackBinder.onRestoreInstanceStateCommand?.invoke(savedInstanceState)
    }

    final override fun onBackPressed() {
        val isSuperMethodCall = !(activityCallbackBinder.onBackPressedCommand?.invoke() ?: false)
        if (isSuperMethodCall) {
            super.onBackPressed()
        }
    }
}