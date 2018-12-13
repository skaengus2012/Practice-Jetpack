package nlab.practice.jetpack.util.databinding.binder

/**
 * @author Doohyun
 */
abstract class Binder<T> {

    abstract fun addCallback(any: T)

    abstract fun removeCallback()

    abstract fun notifyChanged()
}