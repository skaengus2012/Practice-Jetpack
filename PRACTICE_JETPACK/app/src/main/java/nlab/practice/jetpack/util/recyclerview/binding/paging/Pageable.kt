package nlab.practice.jetpack.util.recyclerview.binding.paging

/**
 * @author Doohyun
 */
interface Pageable<T> {
    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
    fun getChangePayload(newItem: T): Any?
}