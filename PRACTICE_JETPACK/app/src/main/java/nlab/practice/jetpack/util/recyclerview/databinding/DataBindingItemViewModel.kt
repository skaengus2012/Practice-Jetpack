package nlab.practice.jetpack.util.recyclerview.databinding

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import nlab.practice.jetpack.util.di.itemview.ItemViewUsecaseFactory

/**
 * @author Doohyun
 */
abstract class DataBindingItemViewModel: BaseObservable() {

    var itemViewUsecaseFactory: ItemViewUsecaseFactory? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int
}