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

import nlab.practice.jetpack.util.recyclerview.DiffCallbackEx
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.model.PagingItem
import org.junit.Assert
import org.junit.Test

/**
 * DiffUtil 의 다른 명칭인 DiffCallback 에 대한 유닛 테스트
 *
 * @author Doohyun
 * @since 2019. 01. 11
 */
class PageableCallbackTest {

    /**
     * Different 을 구현하지 않은 객체에 대해 비교가 잘 이루어 지는가?
     */
    @Test
    fun testCompareNonePagingItem() {
        val callback = DiffCallbackEx<NonePageableItem>()

        val item1 = NonePageableItem()
        val item2 = NonePageableItem()

        val equalCompareResult = callback.areItemsTheSame(item1, item1)
        val diffCompareResult = callback.areItemsTheSame(item1, item2)

        Assert.assertTrue(equalCompareResult)
        Assert.assertFalse(diffCompareResult)
        Assert.assertNull(callback.getChangePayload(item1, item2))
    }

    /**
     * Different 를 구현한 객체의 비교가 잘 이루어지는 가?
     */
    @Test
    fun testComparePagingItem() {
        val callback = DiffCallbackEx<PagingItem>()

        val item1 = PagingItem(1, "One")
        val newItem1 = PagingItem(1, "new One")
        val equalsContentItem1 = PagingItem(1, "One")

        val equalTheSame = callback.areItemsTheSame(item1, newItem1)
        val equalTheContents = callback.areContentsTheSame(item1, equalsContentItem1)
        val diffTheContents = callback.areContentsTheSame(item1, newItem1)

        Assert.assertTrue(equalTheSame)
        Assert.assertTrue(equalTheContents)
        Assert.assertFalse(diffTheContents)
        Assert.assertEquals(newItem1, callback.getChangePayload(item1, newItem1))
    }
}