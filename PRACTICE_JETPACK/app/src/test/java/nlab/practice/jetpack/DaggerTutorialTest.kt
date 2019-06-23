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

import android.view.View
import nlab.practice.jetpack.util.di.itemview.DaggerItemViewUsecaseFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Dagger 의 학습 테스트를 위한 유닛 테스트 정의
 *
 * @author Doohyun
 * @since 2018. 12. 28
 */
class DaggerTutorialTest {

    private lateinit var mockView: View

    @Before
    fun setup() {
        mockView = mock(View::class.java)
    }

    @Test
    fun testScopeForItemViewUsecaseComponent() {
        val aFactory = DaggerItemViewUsecaseFactory
                .builder()
                .setView(mockView)
                .build()

        // 같은 컴포넌트에서 나온 Usecase 는 같은가?
       // Assert.assertEquals(aFactory.navigateViewUsecase(), aFactory.navigateViewUsecase())

        val bFactory = DaggerItemViewUsecaseFactory
                .builder()
                .setView(mockView)
                .build()

        // 다른 컴포넌트에서 나온 Usecase 는 다른가?
       // Assert.assertNotEquals(aFactory.navigateViewUsecase(), bFactory.navigateViewUsecase())
    }


}