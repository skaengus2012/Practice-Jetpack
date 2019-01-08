package nlab.practice.jetpack

import nlab.practice.jetpack.util.recyclerview.binding.paging.Pageable
import nlab.practice.jetpack.util.recyclerview.binding.paging.PageableCallbackEx
import org.junit.Assert
import org.junit.Test

/**
 * DiffUtil 의 다른 명칭인 PageableCallback 에 대한 유닛 테스트
 *
 * @author Doohyun
 * @since 2019. 01. 11
 */
class PageableCallbackTest {

    private class NonePageableItem

    private data class PagingItem(private val _id: Int, private val _title: String) : Pageable<PagingItem> {
        override fun areItemsTheSame(newItem: PagingItem): Boolean {
           return _id == newItem._id
        }

        override fun areContentsTheSame(newItem: PagingItem): Boolean {
            return  areItemsTheSame(newItem) and (_title == newItem._title)
        }

        override fun getChangePayload(newItem: PagingItem): Any? = newItem
    }

    /**
     * Pageable 을 구현하지 않은 객체에 대해 비교가 잘 이루어 지는가?
     */
    @Test
    fun testNonePagingItem() {
        val callback = PageableCallbackEx<NonePageableItem>()

        val item1 = NonePageableItem()
        val item2 = NonePageableItem()

        val equalCompareResult = callback.areItemsTheSame(item1, item1)
        val diffCompareResult = callback.areItemsTheSame(item1, item2)

        Assert.assertTrue(equalCompareResult)
        Assert.assertFalse(diffCompareResult)
        Assert.assertNull(callback.getChangePayload(item1, item2))
    }

    @Test
    fun testPagingItem() {
        val callback = PageableCallbackEx<PagingItem>()

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