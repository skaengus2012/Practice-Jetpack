package nlab.practice.jetpack.util.recyclerview.paging.positional

/**
 * @author Doohyun
 */
interface CountablePositionalRs<T> {
    fun getTotalCount(): Int
    fun getItems(): List<T>
}