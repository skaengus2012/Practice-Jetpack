package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import java.lang.ref.WeakReference

/**
 * @author Doohyun
 */
class PropertyBinder<T, OBS>(private val _target: T) : Binder<OBS>() {

    private var _observable: OBS? = null
    private val _onPropertyChangedCallbacks = ArrayList<DefaultActionOnPropertyChangedCallback<T, OBS>>()

    override fun addCallback(any: OBS) {
        _observable = any

        _onPropertyChangedCallbacks.forEach {
            convertObservable()
                    ?.run { addOnPropertyChangedCallback(it) }
                    ?:kotlin.run { it.changeCallback(_target, any) }
        }
    }

    override fun removeCallback() {
        _onPropertyChangedCallbacks.forEach { convertObservable()?.removeOnPropertyChangedCallback(it) }
    }

    override fun notifyChanged() {
        convertObservable()?.notifyChange()
    }

    private fun isValidPropertyId(propertyIds: IntArray, propertyId: Int) : Boolean =
            propertyIds.isEmpty() or (propertyId == BR._all) or (propertyId in propertyIds)

    private fun convertObservable(): BaseObservable? = _observable as? BaseObservable

    private abstract class DefaultActionOnPropertyChangedCallback<T, OBS>(val changeCallback: T.(OBS) -> Unit):
            Observable.OnPropertyChangedCallback()

    fun drive(vararg propertyIds: Int, changeCallback: T.(OBS) -> Unit): PropertyBinder<T, OBS> {
        val weakRef  = WeakReference(_target)

        val propertyCallback = object: DefaultActionOnPropertyChangedCallback<T, OBS>(changeCallback) {

            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _observable?.let {
                    if (isValidPropertyId(propertyIds, propertyId)) {
                        weakRef.get()?.let { target -> changeCallback(target, it)}
                    }
                }
            }
        }
        _onPropertyChangedCallbacks.add(propertyCallback)

        _observable?.run {
            changeCallback(_target, this)
            convertObservable()
                    ?.run { addOnPropertyChangedCallback(propertyCallback) }
                    ?:kotlin.run { propertyCallback.changeCallback(_target, this) }
        }

        return this
    }
}