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

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith


/**
 * ViewModel Injection 에 대한 테스트 케이스 정의
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AndroidExampleTest {

    private lateinit var application: Application

    @get:Rule
    val activityTest: ActivityTestRule<AnkoFirstActivity> = ActivityTestRule(AnkoFirstActivity::class.java)

    @Before
    fun init() {
        application = activityTest.activity.application
    }

    @Test
    fun testExample() {
        Assert.assertEquals(true, true)
    }
}
