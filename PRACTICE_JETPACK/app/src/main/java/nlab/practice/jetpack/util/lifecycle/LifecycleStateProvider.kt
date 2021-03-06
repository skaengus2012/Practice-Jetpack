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

package nlab.practice.jetpack.util.lifecycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

/**
 * @author Doohyun
 */
interface LifecycleStateProvider {
    fun <T : LifecycleState> get(clazz: KClass<T>, factory: LifecycleState.Factory? = null): T
}

inline fun <reified T : LifecycleState> LifecycleStateProvider.get(
    factory: LifecycleState.Factory? = null
): T {
    return get(T::class, factory)
}

object LifecycleStateProviders {
    fun of(activity: FragmentActivity): LifecycleStateProvider = ActivitySavedStateProvider(activity)
    fun of(fragment: Fragment): LifecycleStateProvider = FragmentSavedStateProvider(fragment)
}

class ActivitySavedStateProvider(private val activity: FragmentActivity) : LifecycleStateProvider {

    override fun <T : LifecycleState> get(clazz: KClass<T>, factory: LifecycleState.Factory?): T {
        return ViewModelProviders.of(activity, factory).get(clazz.java)
    }
}

class FragmentSavedStateProvider(private val fragment: Fragment) : LifecycleStateProvider {

    override fun <T : LifecycleState> get(clazz: KClass<T>, factory: LifecycleState.Factory?): T {
        return ViewModelProviders.of(fragment, factory).get(clazz.java)
    }
}