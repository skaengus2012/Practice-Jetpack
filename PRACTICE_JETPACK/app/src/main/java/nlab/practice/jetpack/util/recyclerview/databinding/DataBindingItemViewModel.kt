package nlab.practice.jetpack.util.recyclerview.databinding

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import nlab.practice.jetpack.common.di.itemview.ItemViewUsecaseComponent

/**
 * @author Doohyun
 */
abstract class DataBindingItemViewModel: BaseObservable() {

    var itemViewUsecaseComponent: ItemViewUsecaseComponent? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int
}