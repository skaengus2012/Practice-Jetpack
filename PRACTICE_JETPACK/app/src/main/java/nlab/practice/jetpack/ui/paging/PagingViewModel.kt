package nlab.practice.jetpack.ui.paging

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter

/**
 * fragment_paging 을 공통으로 사용하기 위한 인터페이스 정의
 *
 * @author Doohyun
 * @since 2019. 01. 29
 */
interface PagingViewModel : ListErrorPageViewModel {

    fun getListAdapter(): BindingPagedListAdapter<PagingItemPracticeViewModel>

    fun getSubTitle(): ObservableField<String>

    fun getBannerText(): String

    fun isShowRefreshProgressBar(): ObservableBoolean

    fun addItems()

    fun onClickBackButton()
}