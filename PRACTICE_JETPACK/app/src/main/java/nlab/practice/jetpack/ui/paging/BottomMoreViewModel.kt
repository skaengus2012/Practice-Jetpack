package nlab.practice.jetpack.ui.paging

import androidx.databinding.Bindable
import nlab.practice.jetpack.BR
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 * @since 2019. 01. 30
 */
class BottomMoreViewModel(private val _onClick: () -> Unit) : BindingItemViewModel() {

    @Bindable
    var isShowProgress = true
    set(value) {
        field = value
        notifyPropertyChanged(BR.isShowProgress)
    }

    override fun getLayoutRes(): Int = R.layout.view_paging_bottom_more

    fun onClickErrorButton() {
        isShowProgress = true
        _onClick()
    }
}