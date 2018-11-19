package nlab.practice.jetpack.util.databinding.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 일반적인 Item 에 대한 ViewHolder
 *
 * @author Doohyun
 */
class ViewComponentBindingHolder(view: View) : RecyclerView.ViewHolder(view){

    fun onBind(itemViewModel: ViewComponentBindingItem) {
        itemViewModel.notifyChange()
    }
}

