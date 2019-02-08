package nlab.practice.jetpack.ui.common.viewmodel

import androidx.databinding.ObservableBoolean

/**
 * view_list_error_page.xml 에 대응되는 뷰모델 정의
 *
 * @author Doohyun
 * @since 2019. 02. 08
 */
interface ListErrorPageViewModel {
    fun isShowErrorView(): ObservableBoolean
    fun refresh()
}