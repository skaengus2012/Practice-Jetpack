package nlab.practice.jetpack.model

import androidx.databinding.library.baseAdapters.BR
import nlab.practice.jetpack.ui.databinding.drive
import nlab.practice.jetpack.ui.viewmodel.CompanyViewModel

/**
 * UnitTest 를 위한 AnkoComponent
 *
 * @author Doohyun
 */
class UnitTestAnkoComponent(private val _viewModel: CompanyViewModel) {

    fun createView() : UnitTestView = UnitTestView().drive(_viewModel, BR.name, BR.subCompany) {
        display(it.name)
        it.subCompany?.name?.run { display(this) }
    }
}