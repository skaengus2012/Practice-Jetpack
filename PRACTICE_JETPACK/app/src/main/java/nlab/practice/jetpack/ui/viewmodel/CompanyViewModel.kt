package nlab.practice.jetpack.ui.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import nlab.practice.jetpack.BR

/**
 * @author Doohyun
 * @since 2018. 11. 09
 */
class CompanyViewModel : BaseObservable() {

    @Bindable var name: String? = null
    set(value) {
        field = value
        notifyPropertyChanged(BR.name)
    }

    @Bindable var subCompany: CompanyViewModel? = null
    set(value) {
        field = value
        notifyPropertyChanged(BR.subCompany)
    }
}