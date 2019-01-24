package nlab.practice.jetpack.util.recyclerview.paging

/**
 * @author Doohyun
 */
interface Pageable<T> {
    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
    fun getChangePayload(newItem: T): Any?
}