package nlab.practice.jetpack.util.recyclerview.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.util.di.itemview.DaggerItemViewUsecaseFactory
import nlab.practice.jetpack.util.di.itemview.ItemViewUsecaseFactory
import nlab.practice.jetpack.util.recyclerview.AbsGenericItemAdapter

/**
 * DataBinding 을 통해 RecyclerView 에 Item 을 셋할 경우, 생성되는 ViewHolder
 *
 * @author Doohyun
 */
class BindingItemViewHolder(private val _viewDataBinding: ViewDataBinding) :
        AbsGenericItemAdapter.GenericItemViewHolder<BindingItemViewModel>(_viewDataBinding.root) {

    var currentViewModel: BindingItemViewModel? = null

    // ItemViewModel 에서 View 관련 레퍼런스 사용 시, 필요한 Usecase 를 정의한 컴포넌트
    private val _itemViewUsecaseFactory: ItemViewUsecaseFactory by lazy {
        DaggerItemViewUsecaseFactory
                .builder()
                .setViewHoler(this)
                .setView(_viewDataBinding.root)
                .build()
    }

    override fun onBind(item: BindingItemViewModel) {
        currentViewModel = item
        item.itemViewUsecaseFactory = _itemViewUsecaseFactory

        // FIXME 적절한 아이디로 수정 필요
        // -> 수정되었음 모든 ID 를 viewModel
        _viewDataBinding.run {
            setVariable(BR.viewModel, item)
            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): BindingItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            val viewDataBinding =
                    DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

            return BindingItemViewHolder(viewDataBinding)
        }
    }
}