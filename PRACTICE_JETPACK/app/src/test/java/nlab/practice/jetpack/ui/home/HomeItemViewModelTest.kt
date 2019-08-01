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

private const val MOCK_MESSAGE_TITLE = "MOCK_MESSAGE_TITLE"
private const val MOCK_MESSAGE_CARD_TITLE = "MOCK_MESSAGE_CARD_TITLE"
private const val MOCK_RESOURCE_COLOR = 5

/**
 * Test for HomeItemViewModel
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class HomeItemViewModelTest {

    @Mock
    private lateinit var clickAction: () -> Unit

    private val titleNotEmptyMenu = TestMenu(
        title = MOCK_MESSAGE_TITLE,
        cardColorRes = MOCK_RESOURCE_COLOR,
        cardTitle = MOCK_MESSAGE_CARD_TITLE
    )

    private val titleEmptyMenu = TestMenu(
        title = MOCK_MESSAGE_TITLE,
        cardColorRes = MOCK_RESOURCE_COLOR,
        cardTitle = ""
    )

    private val titleNullMenu = TestMenu(
        title = MOCK_MESSAGE_TITLE,
        cardColorRes = MOCK_RESOURCE_COLOR
    )

    @Test
    fun checkMappings() {
        with(HomeItemViewModel(titleNotEmptyMenu, clickAction)) {
            assertEquals(MOCK_RESOURCE_COLOR, cardColor)
            assertEquals(MOCK_MESSAGE_TITLE, title)
            assertEquals(MOCK_MESSAGE_CARD_TITLE, cardTitle)
            assertEquals(true, visibleCardTitle)
        }

        with(HomeItemViewModel(titleEmptyMenu,  clickAction)) {
            assertEquals(MOCK_RESOURCE_COLOR, cardColor)
            assertEquals(MOCK_MESSAGE_TITLE, title)
            assertEquals("", cardTitle)
            assertEquals(false, visibleCardTitle)
        }

        with(HomeItemViewModel(titleNullMenu,  clickAction)) {
            assertEquals(MOCK_RESOURCE_COLOR, cardColor)
            assertEquals(MOCK_MESSAGE_TITLE, title)
            assertNull(cardTitle)
            assertEquals(false, visibleCardTitle)
        }
    }

    @Test
    fun onClick() = with(HomeItemViewModel(titleNotEmptyMenu, clickAction)) {
        onClick()
        Mockito.verify(clickAction, Mockito.times(1)).invoke()
    }

}