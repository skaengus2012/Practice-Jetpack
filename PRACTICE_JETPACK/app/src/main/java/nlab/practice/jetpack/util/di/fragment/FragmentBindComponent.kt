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

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.DispatchingAndroidInjector

/**
 * Fragment Binding 을 담당하는 Component
 *
 * @author Doohyun
 */
@Subcomponent(modules = [
    FragmentBindModule::class
])
interface FragmentBindComponent {

    fun activityInjector(): DispatchingAndroidInjector<Fragment>

    /**
     * 공통으로 써야하는 정보가 있다면 이 곳에서 파라미터로 받을 것.
     */
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setOwner(fragment: Fragment): Builder

        fun build() : FragmentBindComponent
    }
}