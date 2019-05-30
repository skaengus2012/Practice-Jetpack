package nlab.practice.jetpack.util.recyclerview

/**
 * @author Doohyun
 */
interface Different<T> {
    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
    fun getChangePayload(newItem: T): Any?
}

class DifferentDelegate<ViewModel, Item: Different<Item>>(
        private val _myItemSupplier: ()-> Item,
        private val _itemConvertFunction: (viewModel: ViewModel) -> Item) : Different<ViewModel> {

    override fun areItemsTheSame(newItem: ViewModel): Boolean {
        return _myItemSupplier().areItemsTheSame(_itemConvertFunction(newItem))
    }

    override fun areContentsTheSame(newItem: ViewModel): Boolean {
        return _myItemSupplier().areContentsTheSame(_itemConvertFunction(newItem))
    }

    override fun getChangePayload(newItem: ViewModel): Any? {
        return _myItemSupplier().getChangePayload(_itemConvertFunction(newItem))
    }
}