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

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.ui.main.MainBottomNavUsecase
import nlab.practice.jetpack.ui.main.MainHolderViewModel
import nlab.practice.jetpack.util.di.activity.ActivityCallback
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.lifecycle.ActivityLifeCycleBinder
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * @author Doohyun
 */
class MainHolderTest {

    private val activityLifeCycleBinder =
        ActivityLifeCycleBinder(CompositeDisposable())
    private val activityCallbackDelegate = ActivityCallback()

    private lateinit var mainBottomNavUsecase: MainBottomNavUsecase

    @Before
    fun setup() {
        mainBottomNavUsecase = mock(MainBottomNavUsecase::class.java)
    }

    @Test
    fun testSimpleMainViewModel() {
        val mainViewModel =
                MainHolderViewModel(activityLifeCycleBinder, activityCallbackDelegate, mainBottomNavUsecase)

        activityLifeCycleBinder.apply(ActivityLifeCycle.ON_CREATE)
    }
}