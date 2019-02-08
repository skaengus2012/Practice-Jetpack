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

    var adapterPosition: Int? = null
    var itemViewModel: BindingItemViewModel? = null

    // ItemViewModel 에서 View 관련 레퍼런스 사용 시, 필요한 Usecase 를 정의한 컴포넌트
    private val _itemViewUsecaseFactory: ItemViewUsecaseFactory by lazy {
        DaggerItemViewUsecaseFactory
                .builder()
                .setView(_viewDataBinding.root)
                .build()
    }

    override fun onBind(adapterPosition: Int, item: BindingItemViewModel) {
        this.adapterPosition = adapterPosition

        this.itemViewModel = item.apply {
            itemViewUsecaseFactory = _itemViewUsecaseFactory

            _viewDataBinding.setVariable(BR.viewModel, this)
            _viewDataBinding.executePendingBindings()
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