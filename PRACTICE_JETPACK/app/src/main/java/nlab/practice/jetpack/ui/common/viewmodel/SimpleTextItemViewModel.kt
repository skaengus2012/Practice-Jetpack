package nlab.practice.jetpack.ui.common.viewmodel

import androidx.databinding.Bindable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
class SimpleTextItemViewModel (@Bindable val text: String) : BindingItemViewModel() {

    override fun getLayoutRes(): Int = R.layout.view_simple_text_item
}