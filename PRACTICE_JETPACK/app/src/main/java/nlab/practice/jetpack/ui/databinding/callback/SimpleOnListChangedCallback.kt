package nlab.practice.jetpack.ui.databinding.callback

import androidx.databinding.ObservableList

/**
 * RecyclerView 의 어떤 변화에도 단순하게 onChange 가 등장하도록 처리된 콜백
 *
 * @author Doohyun
 * @since 2018. 11. 16
 */
abstract class SimpleOnListChangedCallback<ITEM> : ObservableList.OnListChangedCallback<ObservableList<ITEM>>() {

    override fun onItemRangeRemoved(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
       onChanged(sender)
    }

    override fun onItemRangeMoved(sender: ObservableList<ITEM>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeInserted(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeChanged(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }
}