package nlab.practice.jetpack.util.recyclerview.binding

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import nlab.practice.jetpack.util.di.itemview.ItemViewUsecaseFactory

/**
 * @author Doohyun
 */
abstract class BindingItemViewModel: BaseObservable() {
    var itemViewUsecaseFactory: ItemViewUsecaseFactory? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int
}