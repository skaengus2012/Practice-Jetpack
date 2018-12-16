package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.BaseObservable

/**
 * [_propertyBinder] 를 [_mapper] 조건에 따라 Binder 의 Generic 을 변경하는 호환자 정의
 *
 * @author Doohyun
 */
class PropertyMappingBindAdapter<T, OBS, PROP: BaseObservable>(
        private val _mapper: (OBS) -> PROP,
        private val _propertyBinder: PropertyBinder<T, PROP>): Binder<OBS> {

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