package nlab.practice.jetpack.util.recyclerview.selection

/**
 * @author Doohyun
 * @since 2019. 02. 08
 */
interface Selectable<T> {
    fun getSelectionKey(): T
}