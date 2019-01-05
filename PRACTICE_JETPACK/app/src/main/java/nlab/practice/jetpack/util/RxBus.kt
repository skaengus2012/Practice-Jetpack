package nlab.practice.jetpack.util

import com.jakewharton.rxrelay2.PublishRelay

/**
 * @author Doohyun
 */
class RxBus {

    companion object {
        fun post(event: Any) {
            RxBusInternal.relay.accept(event)
        }

        inline fun <reified T> toObservable() = RxBusInternal.relay.ofType(T::class.java)
    }

    object RxBusInternal {
        val relay = PublishRelay.create<Any>().toSerialized()
    }
}