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

package nlab.practice.jetpack.ui.tutorial

import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.lifecycle.ActivityLifecycleBinder
import nlab.practice.jetpack.testSchedulerFactoryOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.*
import org.mockito.Mockito.`when`

private const val MOCK_MESSAGE_FIRST = "Hello"
private const val MOCK_MESSAGE_CHANGE_TEXT = "CHANGE TEXT"
private const val MOCK_MESSAGE_CHANGE_TEXT_DELAYED = "CHANGE TEXT - DELAYED"

/**
 * Test for AnkoFirstViewModel
 *
 * 0. 초기상태 텍스트
 * 1. 텍스트 변경
 * 2. 딜레이 텍스트 변경
 * 3. 화면회전에 대한 상태유지
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class AnkoFirstViewModelTest {

    private val lifecycleBinder = ActivityLifecycleBinder()

    private val schedulerFactory = testSchedulerFactoryOf()

    private val lifecycleState = AnkoFirstState()

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    @Before
    fun initialize() {
        initializeResourceProvider()
    }

    private fun createViewModel() = AnkoFirstViewModel(
        lifecycleBinder,
        schedulerFactory,
        resourceProvider,
        lifecycleState
    )

    private fun initializeResourceProvider() {
        `when`(resourceProvider.getString(R.string.anko_first_message)).thenReturn(MOCK_MESSAGE_FIRST)
        `when`(resourceProvider.getString(R.string.anko_first_btn_change_text)).thenReturn(MOCK_MESSAGE_CHANGE_TEXT)
        `when`(resourceProvider.getString(R.string.anko_first_btn_change_text_delay)).thenReturn(MOCK_MESSAGE_CHANGE_TEXT_DELAYED)
    }

    @Test
    fun testInitialize() {
        assertEquals(createViewModel().message.get(), MOCK_MESSAGE_FIRST)
    }

    @Test
    fun testChangeText() {
        with(createViewModel()) {
            changeText()
            assertEquals(message.get(), MOCK_MESSAGE_CHANGE_TEXT)
        }
    }

    @Test
    fun testChangeTextDelayTime() {
        with(createViewModel()) {
            changeTextDelayTime()
            assertEquals(message.get(), MOCK_MESSAGE_CHANGE_TEXT_DELAYED)
        }
    }

    @Test
    fun when_OrientationChanged_expected_TextStateRemained() {
        createViewModel().changeText()
        assertEquals(createViewModel().message.get(), MOCK_MESSAGE_CHANGE_TEXT)
    }
}