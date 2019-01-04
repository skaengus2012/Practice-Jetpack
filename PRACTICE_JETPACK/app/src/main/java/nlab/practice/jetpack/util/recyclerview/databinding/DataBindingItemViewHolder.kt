package nlab.practice.jetpack.util.recyclerview.databinding

import androidx.databinding.ViewDataBinding
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.util.di.itemview.DaggerItemViewUsecaseFactory
import nlab.practice.jetpack.util.di.itemview.ItemViewUsecaseFactory
import nlab.practice.jetpack.util.recyclerview.GenericItemAdapter

/**
 * DataBinding 을 통해 RecyclerView 에 Item 을 셋할 경우, 생성되는 ViewHolder
 *
 * @author Doohyun
 */
class DataBindingItemViewHolder<T: DataBindingItemViewModel>(private val _viewDataBinding: ViewDataBinding) :
        GenericItemAdapter.GenericItemViewHolder<T>(_viewDataBinding.root) {

    // ItemViewModel 에서 View 관련 레퍼런스 사용 시, 필요한 Usecase 를 정의한 컴포넌트
    private val _itemViewUsecaseFactory: ItemViewUsecaseFactory by lazy {
        DaggerItemViewUsecaseFactory
                .builder()
                .setView(_viewDataBinding.root)
                .build()
    }

    override fun onBind(item: T) {
        item.itemViewUsecaseFactory = _itemViewUsecaseFactory

        // FIXME 적절한 아이디로 수정 필요
        // -> 수정되었음 모든 ID 를 viewModel
        _viewDataBinding.run {
            setVariable(BR.viewModel, item)
            executePendingBindings()
        }
    }
}