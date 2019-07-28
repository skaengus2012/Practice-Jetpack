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

package nlab.practice.jetpack

import androidx.multidex.MultiDexApplication
import dagger.android.HasAndroidInjector
import io.reactivex.plugins.RxJavaPlugins
import nlab.practice.jetpack.util.di.AppComponent
import nlab.practice.jetpack.util.di.DaggerAppComponent
import nlab.practice.jetpack.util.di.AndroidInjectorEx
import timber.log.Timber

/**
 * @author Doohyun
 */
class JetPackApplication : MultiDexApplication(), AppComponent.Supplier, HasAndroidInjector {

    private lateinit var _appComponent: AppComponent

    private val activityInjector = AndroidInjectorEx()

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
            Timber.e(it, "initRxJavaErrorHandler ${it.localizedMessage}")
        }
    }

    override fun getAppComponent(): AppComponent = _appComponent

    override fun androidInjector() = activityInjector
}