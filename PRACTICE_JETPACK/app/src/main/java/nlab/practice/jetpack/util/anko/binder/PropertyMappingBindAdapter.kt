package nlab.practice.jetpack.util.anko.binder

import androidx.databinding.BaseObservable

/**
 * [propertyBinder] 를 [mapper] 조건에 따라 Binder 의 Generic 을 변경하는 호환자 정의
 *
 * @author Doohyun
 */
class PropertyMappingBindAdapter<T, OBS, PROP: BaseObservable>(
        private val mapper: (OBS) -> PROP,
        private val propertyBinder: PropertyBinder<T, PROP>): Binder<OBS> {

    override fun addCallback(any: OBS) {
        propertyBinder.addCallback(mapper(any))
    }

    override fun removeCallback() {
        propertyBinder.removeCallback()
    }

    override fun notifyChanged() {
        propertyBinder.notifyChanged()
    }
}