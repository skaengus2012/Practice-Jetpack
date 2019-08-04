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

package nlab.practice.jetpack.util.recyclerview

import org.junit.Test

import org.junit.Assert.*

/**
 * Test for Different
 *
 * 1. Different 에 대한 사용 예시 정의
 * 2. DifferentDelegate 에 대한 사용 예시 정의 및 기능 테스트
 *
 * @author Doohyun
 */
class DifferentTest {

    private val differTarget = DifferentTestVO(1)

    private val copyDifferTarget = differTarget.copy()

    private val supportPayloadTarget = DifferentTestPayloadSupportVO(1)

    private val copySupportPayloadTarget = supportPayloadTarget.copy()

    @Test
    fun checkValidDifferent() {
        assertTrue(differTarget.areContentsTheSame(copyDifferTarget))
        assertFalse(differTarget.areItemsTheSame(copyDifferTarget))
        assertNull(differTarget.getChangePayload(copyDifferTarget))

        assertTrue(supportPayloadTarget.areContentsTheSame(copySupportPayloadTarget))
        assertFalse(supportPayloadTarget.areItemsTheSame(copySupportPayloadTarget))
        assertEquals(copySupportPayloadTarget, supportPayloadTarget.getChangePayload(copySupportPayloadTarget))
    }

    @Test
    fun checkDifferentHasItem() {
        val item = DifferentHasItem(differTarget)
        val copyUseItem = DifferentHasItem(copyDifferTarget)

        assertTrue(item.areContentsTheSame(copyUseItem))
        assertFalse(item.areItemsTheSame(copyUseItem))
        assertNull(item.getChangePayload(copyUseItem))

        val supportPayloadItem = DifferentHasItem(supportPayloadTarget)
        val copySupportPayloadItem = DifferentHasItem(copySupportPayloadTarget)

        assertTrue(supportPayloadItem.areContentsTheSame(copySupportPayloadItem))
        assertFalse(supportPayloadItem.areItemsTheSame(copySupportPayloadItem))
        assertEquals(copySupportPayloadItem, supportPayloadItem.getChangePayload(copySupportPayloadItem))
    }

    @Test
    fun checkDifferCallbackEx() {
        with(DiffCallbackEx<SimpleVO>()) {
            val vo = SimpleVO()
            assertTrue(areItemsTheSame(vo, vo))
            assertFalse(areItemsTheSame(vo, SimpleVO()))
            assertFalse(areContentsTheSame(vo, SimpleVO()))
            assertNull(getChangePayload(vo, SimpleVO()))
        }

        with(DiffCallbackEx<SimpleDataVO>()) {
            val vo = SimpleDataVO(1)
            assertTrue(areItemsTheSame(vo, vo))
            assertFalse(areItemsTheSame(vo, vo.copy()))
            assertTrue(areContentsTheSame(vo, vo.copy()))
            assertFalse(areContentsTheSame(vo, SimpleDataVO(2)))
            assertNull(getChangePayload(vo, vo.copy()))
        }

        with(DiffCallbackEx<DifferentTestVO>()) {
            assertTrue(areItemsTheSame(differTarget, differTarget))
            assertFalse(areItemsTheSame(differTarget, copyDifferTarget))
            assertTrue(areContentsTheSame(differTarget, copyDifferTarget))
            assertFalse(areContentsTheSame(differTarget, DifferentTestVO(3)))
            assertNull(getChangePayload(differTarget, copyDifferTarget))
        }

        with(DiffCallbackEx<DifferentTestPayloadSupportVO>()) {
            assertTrue(areItemsTheSame(supportPayloadTarget, supportPayloadTarget))
            assertFalse(areItemsTheSame(supportPayloadTarget, copySupportPayloadTarget))
            assertTrue(areContentsTheSame(supportPayloadTarget, copySupportPayloadTarget))
            assertFalse(areContentsTheSame(supportPayloadTarget, DifferentTestPayloadSupportVO(4)))
            assertEquals(copySupportPayloadTarget, getChangePayload(supportPayloadTarget, copySupportPayloadTarget))
        }

        with(DiffCallbackEx<DifferentHasItem<DifferentTestVO>>()) {
            val item = DifferentHasItem(differTarget)
            val copyUseItem = DifferentHasItem(copyDifferTarget)

            assertTrue(areItemsTheSame(item, item))
            assertFalse(areItemsTheSame(item, copyUseItem))
            assertTrue(areContentsTheSame(item, copyUseItem))
            assertFalse(areContentsTheSame(item, DifferentHasItem(DifferentTestVO(5))))
            assertNull(getChangePayload(item, copyUseItem))
        }

        with(DiffCallbackEx<DifferentHasItem<DifferentTestPayloadSupportVO>>()) {
            val supportPayloadItem = DifferentHasItem(supportPayloadTarget)
            val copySupportPayloadItem = DifferentHasItem(copySupportPayloadTarget)

            assertTrue(areItemsTheSame(supportPayloadItem, supportPayloadItem))
            assertFalse(areItemsTheSame(supportPayloadItem, copySupportPayloadItem))
            assertTrue(areContentsTheSame(supportPayloadItem, copySupportPayloadItem))
            assertFalse(areContentsTheSame(supportPayloadItem, DifferentHasItem(DifferentTestPayloadSupportVO(6))))
            assertEquals(copySupportPayloadItem, getChangePayload(supportPayloadItem, copySupportPayloadItem))
        }
    }
}

private class SimpleVO

private data class SimpleDataVO(private val field: Int)

private data class DifferentTestVO(private val field: Int) : Different<DifferentTestVO> {
    override fun areItemsTheSame(newItem: DifferentTestVO) = (this === newItem)
    override fun areContentsTheSame(newItem: DifferentTestVO) = (this == newItem)
}

private data class DifferentTestPayloadSupportVO(private val field: Int) : Different<DifferentTestPayloadSupportVO> {
    override fun areItemsTheSame(newItem: DifferentTestPayloadSupportVO): Boolean = (this === newItem)
    override fun areContentsTheSame(newItem: DifferentTestPayloadSupportVO): Boolean = (this ==  newItem)
    override fun getChangePayload(newItem: DifferentTestPayloadSupportVO): Any? = newItem
}

private class DifferentHasItem<T : Different<T>>(private val data: T) :
    Different<DifferentHasItem<T>> by DifferentDelegate(data, { it.data })

