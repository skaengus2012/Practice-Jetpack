package nlab.practice.jetpack.util.databinding.binder

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import java.lang.ref.WeakReference

/**
 * BaseObservable 의 Property 를 Support 하는 Binder
 *
 * @author Doohyun
 */
class PropertyBinder<T, OBS>(private val _target: T) : Binder<OBS>() {

    private var _observable: OBS? = null
    private val _onPropertyChangedCallbacks = ArrayList<DefaultActionOnPropertyChangedCallback<T, OBS>>()

    /**
     * [any] 에 내부 생성된 Callback 을 추가한다.
     * [any] 가 BaseObservable 이 아니라면, Callback 추가를 하지 않고, drive 에서 정의된 기능을 수행한다.
     *
     * @param any 리스너를 추가할 대상
     */
    override fun addCallback(any: OBS) {
        _observable = any

        _onPropertyChangedCallbacks.forEach {
            _observable?.toObservable()
                    ?.run { addOnPropertyChangedCallback(it) }
                    ?:kotlin.run { it.changeCallback(_target, any) }
        }
    }

    /**
     * [_observable] 에 추가된 모든 Callback 을 삭제한다.
     */
    override fun removeCallback() {
        _onPropertyChangedCallbacks.forEach { _observable?.toObservable()?.removeOnPropertyChangedCallback(it) }
    }

    /**
     * [_observable] 알림 처리
     */
    override fun notifyChanged() {
        _observable?.toObservable()?.notifyChange()
    }

    /**
     * 알림으로 등장한 [propertyId] 가 유효한가?
     *
     * 조건
     *  [propertyId] 가 [propertyIds] 에 속해있음
     *  [propertyId] 가 all 임
     *
     * @param propertyIds   리스너에서 서포트하는 목록
     * @param propertyId    업데이트 항목
     * @return              유효 여부
     */
    private fun isValidPropertyId(propertyIds: IntArray, propertyId: Int) : Boolean = when(propertyId) {
        BR._all -> true
        in propertyIds -> true
        else -> false
    }

    private fun OBS.toObservable(): BaseObservable? = this as? BaseObservable

    private abstract class DefaultActionOnPropertyChangedCallback<T, OBS>(val changeCallback: T.(OBS) -> Unit):
            Observable.OnPropertyChangedCallback()

    /**
     * [propertyIds] 에 속한 요청에 대해 업데이트 시, [changeCallback] 을 처리하는 리스너를 추가한다.
     *
     * 상세 조건은 PropertyBinder::isValidPropertyId 에 따른다.
     */
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
            _observable?.toObservable()
                    ?.run { addOnPropertyChangedCallback(propertyCallback) }
                    ?:kotlin.run { propertyCallback.changeCallback(_target, this) }
        }

        return this
    }
}