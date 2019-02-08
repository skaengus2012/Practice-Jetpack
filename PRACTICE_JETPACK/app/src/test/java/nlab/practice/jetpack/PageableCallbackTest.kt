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