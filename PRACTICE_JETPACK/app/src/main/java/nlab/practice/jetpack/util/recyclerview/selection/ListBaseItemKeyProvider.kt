package nlab.practice.jetpack.util.recyclerview.selection

import android.util.SparseArray
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

/**
 * 아이템 목록을 기반으로 key-Value 를 제공하는 Provider
 *
 * @author Doohyun
 */
class ListBaseItemKeyProvider<T>: ItemKeyProvider<T>(ItemKeyProvider.SCOPE_CACHED) {

    private val _positionToKeys = SparseArray<T>()

    private val _keyToPositionGroup = HashMap<T, Int>()

    override fun getPosition(key: T): Int = _keyToPositionGroup[key]?: RecyclerView.NO_POSITION

    override fun getKey(position: Int): T? = _positionToKeys[position]

    fun replaceList(newItems: List<Selectable<T>>) {
        _positionToKeys.clear()
        _keyToPositionGroup.clear()

        (0 until newItems.size).forEach {
            val key = newItems[it].getSelectionKey()

            _positionToKeys.append(it, key)
            _keyToPositionGroup[key] = it
        }
    }
}