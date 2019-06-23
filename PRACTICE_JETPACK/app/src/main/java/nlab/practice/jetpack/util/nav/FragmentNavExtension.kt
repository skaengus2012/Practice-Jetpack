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

package nlab.practice.jetpack.util.nav

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.reflect.KClass

/**
 * Fragment Navigation 유지를 위해 필요한 함수
 *
 * @author Doohyun
 */

/**
 * 클래스의 이름으로 태그를 생성한다.
 */
fun <T : Fragment> KClass<T>.fragmentTag(): String = this.java.name

/**
 * [tag] 로 [containerResId] 에 포함된 Fragment 를 찾는따.
 *
 * 만약 Fragment 가 없다면, [defaultProvider] 로부터 Fragment 를 생성하여 add 한다.
 */
inline fun FragmentManager.findFragmentByTag(
        @IdRes containerResId: Int,
        tag: String,
        fragmentTransaction: FragmentTransaction,
        crossinline defaultProvider: () -> Fragment): Fragment {

    return findFragmentByTag(tag) ?: defaultProvider().apply {
        // NOTE : replace 는 Fragment 를 떼고 붙임. add 시, Fragment 자체에는 어떤 영향도 없음
        fragmentTransaction.add(containerResId, this, tag)
    }
}

/**
 * primaryNavigationFragment 를 변경해야하는가?
 */
fun FragmentManager.isNeedChangePrimaryNavigationFragment(targetFragment: Fragment): Boolean {
    return primaryNavigationFragment?.let { it != targetFragment } ?: true
}

