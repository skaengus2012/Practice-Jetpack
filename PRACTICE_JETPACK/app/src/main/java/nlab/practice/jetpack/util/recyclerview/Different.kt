package nlab.practice.jetpack.util.recyclerview

/**
 * @author Doohyun
 */
interface Different<T> {
    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
    fun getChangePayload(newItem: T): Any?
}