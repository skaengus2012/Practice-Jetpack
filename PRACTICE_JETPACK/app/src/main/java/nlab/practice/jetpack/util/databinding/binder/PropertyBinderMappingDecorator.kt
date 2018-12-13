package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.BaseObservable

/**
 * @author Doohyun
 */
class PropertyBinderMappingDecorator<T, OBS, PROP: BaseObservable>(
        private val _mapper: (OBS) ->PROP,
        private val _propertyBinder: PropertyBinder<T, PROP>): Binder<OBS>() {

    override fun addCallback(any: OBS) {
        _propertyBinder.addCallback(_mapper(any))
    }

    override fun removeCallback() {
        _propertyBinder.removeCallback()
    }

    override fun notifyChanged() {
        _propertyBinder.notifyChanged()
    }
}