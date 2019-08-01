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

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.History
import nlab.practice.jetpack.util.ResourceProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.junit.Assert.*
import org.mockito.junit.MockitoJUnitRunner

private const val MOCK_MESSAGE_SUCCESS = "연구 성공"
private const val MOCK_MESSAGE_FAILED = "연구 실패"

/**
 * Test for HistoryItemViewModel
 *
 * 1. 데이터 상태에 따라 제대로된 라벨이 표시되는지 확인 필요.
 *
 * @author Doohyun
 */
@RunWith(MockitoJUnitRunner::class)
class HistoryItemViewModelTest {

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private val sampleHistory = History(
        title = "title",
        subTitle = "sub_title",
        isSuccess = true
    )

    @Before
    fun initialize() {
        `when`(resourceProvider.getString(R.string.history_success)).thenReturn(MOCK_MESSAGE_SUCCESS)
        `when`(resourceProvider.getString(R.string.history_failed)).thenReturn(MOCK_MESSAGE_FAILED)
    }

    @Test
    fun checkMappings() {
        with(HistoryItemViewModel(resourceProvider, sampleHistory)) {
            assertEquals(sampleHistory.title, title)
            assertEquals(sampleHistory.subTitle, subTitle)
            assertEquals(MOCK_MESSAGE_SUCCESS, resultMessage)
        }

        with(HistoryItemViewModel(resourceProvider, sampleHistory.copy(isSuccess = false))) {
            assertEquals(sampleHistory.title, title)
            assertEquals(sampleHistory.subTitle, subTitle)
            assertEquals(MOCK_MESSAGE_FAILED, resultMessage)
        }
    }

}