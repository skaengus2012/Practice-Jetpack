package nlab.practice.jetpack.util.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.databinding.binder.*
import org.jetbrains.anko.AnkoComponent

/**
 * DataBinding 을 처리할 수 있도록 Binder 를 의존하는 AnkoComponent
 *
 * @author Doohyun
 */
abstract class DataBindingAnkoComponent<ViewModel, U> : BaseObservable(), AnkoComponent<U> {

    private var _viewModel: ViewModel? = null
    private val _binders = ObservableArrayList<Binder<ViewModel>>()

    fun setViewModel(viewModel: ViewModel) : DataBindingAnkoComponent<ViewModel, U> {
        _viewModel?.run { _binders.forEach { it.removeCallback() } }

        _viewModel = viewModel
        _binders.forEach { it.addCallback(viewModel) }

        return this
    }

    fun <TARGET, U: BaseObservable> TARGET.binder(mapper: (ViewModel) -> U): PropertyBinder<TARGET, U> {
        val binder = PropertyBinder<TARGET, U>(this)

        PropertyMappingBindAdapter(mapper, binder).apply {
            _viewModel?.run { addCallback(this) }
            _binders.add(this)
        }

        return binder
    }

    fun <TARGET> TARGET.binder(): PropertyBinder<TARGET, ViewModel> {
        return PropertyBinder<TARGET, ViewModel>(this).apply {
            _viewModel?.run { addCallback(this) }
            _binders.add(this)
        }
    }

    fun <TARGET: RecyclerView, ITEM> TARGET.binderList(mapper: (ViewModel)-> List<ITEM>): ListBinder<TARGET, ITEM> {
        val binder = ListBinder<TARGET, ITEM>(this)

        ListMappingBindAdapter(mapper, binder).apply {
            _viewModel?.run { addCallback(this) }
            _binders.add(this)
        }

        return binder
    }
}