package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
class UnboundedPagingViewModel @Inject constructor() : PagingViewModel {

    override fun getListAdapter(): BindingPagedListAdapter<PagingItemViewModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSubTitle(): ObservableField<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isShowRefreshProgressBar(): ObservableBoolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickBackButton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}