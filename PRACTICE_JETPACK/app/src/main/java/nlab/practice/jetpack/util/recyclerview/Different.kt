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
        inline val myItemSupplier: ()-> Item,
        inline val itemConvertFunction: (viewModel: ViewModel) -> Item) : Different<ViewModel> {

    override fun areItemsTheSame(newItem: ViewModel): Boolean {
        return myItemSupplier().areItemsTheSame(itemConvertFunction(newItem))
    }

    override fun areContentsTheSame(newItem: ViewModel): Boolean {
        return myItemSupplier().areContentsTheSame(itemConvertFunction(newItem))
    }

    override fun getChangePayload(newItem: ViewModel): Any? {
        return myItemSupplier().getChangePayload(itemConvertFunction(newItem))
    }
}