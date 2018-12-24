package nlab.practice.jetpack.util.recyclerview.databinding

import android.view.View
import nlab.practice.jetpack.common.di.itemview.DaggerItemViewUsecaseComponent
import nlab.practice.jetpack.common.di.itemview.ItemViewUsecaseModule
import nlab.practice.jetpack.util.recyclerview.GenericItemAdapter

/**
 * DataBinding 을 통해 RecyclerView 에 Item 을 셋할 경우, 생성되는 ViewHolder
 *
 * @author Doohyun
 */
class DataBindingItemViewHolder<T: DataBindingItemViewModel>(view: View) :
        GenericItemAdapter.GenericItemViewHolder<T>(view) {

    // ItemViewModel 에서 View 관련 레퍼런스 사용 시, 필요한 Usecase 를 정의한 컴포넌트
    private val _itemViewUsecaseComponent = DaggerItemViewUsecaseComponent
            .builder()
            .itemViewUsecaseModule(ItemViewUsecaseModule(view))
            .build()

    override fun onBind(item: T) {
        item.itemViewUsecaseComponent = _itemViewUsecaseComponent
    }
}