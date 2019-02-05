package nlab.practice.jetpack.ui.common.viewmodel

import androidx.annotation.LayoutRes
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
class SimpleItemViewModel(@LayoutRes private val _layoutResId: Int): BindingItemViewModel() {

    override fun getLayoutRes(): Int = _layoutResId

}