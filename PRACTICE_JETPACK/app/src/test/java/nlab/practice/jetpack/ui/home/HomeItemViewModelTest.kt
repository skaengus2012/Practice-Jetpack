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

package nlab.practice.jetpack.ui.home

import nlab.practice.jetpack.repository.model.TestMenu
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.junit.Assert.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test for HomeItemViewModel
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class HomeItemViewModelTest {

    @Mock
    private lateinit var clickAction: () -> Unit

    private val sampleMenu = TestMenu(
        title = "title",
        cardColorRes = 5,
        cardTitle = "cardTitle"
    )

    @Test
    fun checkMappings() {
        with(HomeItemViewModel(sampleMenu, clickAction)) {
            assertEquals(sampleMenu.cardColorRes, cardColor)
            assertEquals(sampleMenu.title, title)
            assertEquals(sampleMenu.cardTitle, cardTitle)
            assertEquals(true, visibleCardTitle)
        }

        with(HomeItemViewModel(sampleMenu.copy(cardTitle = ""), clickAction)) {
            assertEquals(sampleMenu.cardColorRes, cardColor)
            assertEquals(sampleMenu.title, title)
            assertEquals("", cardTitle)
            assertEquals(false, visibleCardTitle)
        }

        with(HomeItemViewModel(sampleMenu.copy(cardTitle = null),  clickAction)) {
            assertEquals(sampleMenu.cardColorRes, cardColor)
            assertEquals(sampleMenu.title, title)
            assertNull(cardTitle)
            assertEquals(false, visibleCardTitle)
        }
    }

    @Test
    fun onClick() = with(HomeItemViewModel(sampleMenu, clickAction)) {
        onClick()
        Mockito.verify(clickAction, Mockito.times(1)).invoke()
    }

}