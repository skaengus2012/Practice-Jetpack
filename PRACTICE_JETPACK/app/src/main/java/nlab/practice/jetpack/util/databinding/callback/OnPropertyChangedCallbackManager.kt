package nlab.practice.jetpack.util.databinding.callback

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import java.lang.ref.WeakReference

/**
 * @author Doohyun
 */
class OnPropertyChangedCallbackManager<TARGET, VIEW_MODEL> (
        val targetRef: WeakReference<TARGET>,
        val propertyIds: IntArray,
        val biConsumer: (TARGET, VIEW_MODEL) -> Unit) {

    private val _propertyChangedCallback = WeakPropertyChangedCallbackImpl()

    private var _viewModel: VIEW_MODEL? = null

    fun addCallback(viewModel: VIEW_MODEL, baseObservable: BaseObservable) {
        _viewModel = viewModel
        baseObservable.addOnPropertyChangedCallback(_propertyChangedCallback)
    }

    fun removeCallback(baseObservable: BaseObservable) {
        baseObservable.removeOnPropertyChangedCallback(_propertyChangedCallback)
    }

    inner class WeakPropertyChangedCallbackImpl : Observable.OnPropertyChangedCallback() {

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            _viewModel?.let {
                viewModel
                ->
                sender?.run { onTargetPropertyChanged(viewModel, propertyId) }
            }
        }

        private fun onTargetPropertyChanged(sender: VIEW_MODEL, propertyId: Int) {
            targetRef.get()
                    ?.takeIf { isValidPropertyId(propertyId) }
                    ?.run { biConsumer(this, sender) }
        }

        private fun isValidPropertyId(propertyId: Int) : Boolean =
                propertyIds.isEmpty() or (propertyId == BR._all) or (propertyId in propertyIds)
    }
}