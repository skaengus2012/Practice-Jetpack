package nlab.practice.jetpack.util.databinding.binder

/**
 * Binder 의 기본 인터페이스
 *
 * Callback 을 추가하거나, 삭제, 알림 기능등을 수행
 *
 * @author Doohyun
 */
abstract class Binder<T> {

    abstract fun addCallback(any: T)

    abstract fun removeCallback()

    abstract fun notifyChanged()
}