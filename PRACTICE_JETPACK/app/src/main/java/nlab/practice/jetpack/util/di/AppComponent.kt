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

package nlab.practice.jetpack.util.di

import android.app.Application
import dagger.Component
import javax.inject.Singleton
import dagger.BindsInstance
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import nlab.practice.jetpack.JetPackApplication
import nlab.practice.jetpack.util.di.activity.ActivityBindComponent
import nlab.practice.jetpack.util.di.fragment.FragmentBindComponent
/**
 * @author Doohyun
 */
@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setApplication(application: Application): Builder

        fun build(): AppComponent
    }

    interface Supplier {
        fun getAppComponent(): AppComponent
    }

    fun inject(application: JetPackApplication)

    fun activityBindComponent(): ActivityBindComponent.Builder

    fun fragmentBindComponent(): FragmentBindComponent.Builder
}