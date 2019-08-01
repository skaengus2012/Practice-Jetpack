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

package nlab.practice.jetpack.ui.history

import nlab.practice.jetpack.repository.HistoryRepository
import nlab.practice.jetpack.repository.model.History
import nlab.practice.jetpack.ui.common.viewmodel.SimpleItemViewModel
import nlab.practice.jetpack.util.ResourceProvider
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.junit.Assert.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test for HistoryViewModel
 *
 * 1. ViewModel 생성 시, 아이템이 제대로 추가되었는지 확인
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class HistoryViewModelTest {

    @Mock
    lateinit var repository: HistoryRepository

    @Mock
    lateinit var resourceProvider: ResourceProvider

    @Mock
    lateinit var historyItemFactory: HistoryItemViewModel.Factory

    @Before
    fun initialize() {
        `when`(repository.items).thenReturn(listOf(History(
            title = "title",
            subTitle = "subTitle",
            isSuccess = false
        )))

        historyItemFactory = HistoryItemViewModel.Factory(resourceProvider)
    }

    @Test
    fun setup() = with(HistoryViewModel(repository, historyItemFactory)) {
        assertEquals(1, headers.size)
        assertThat(headers[0], instanceOf(SimpleItemViewModel::class.java))
        assertEquals(1, items.size)
        assertThat(items[0], instanceOf(HistoryItemViewModel::class.java))
    }

}