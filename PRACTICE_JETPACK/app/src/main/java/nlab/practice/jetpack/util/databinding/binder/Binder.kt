package nlab.practice.jetpack.util.databinding.binder

/**
 * Binder 의 기본 인터페이스
 *
 * Callback 을 추가하거나, 삭제, 알림 기능등을 수행
 *
 * @author Doohyun
 */
interface Binder<T> {

    fun addCallback(any: T)

    fun removeCallback()

    fun notifyChanged()

}