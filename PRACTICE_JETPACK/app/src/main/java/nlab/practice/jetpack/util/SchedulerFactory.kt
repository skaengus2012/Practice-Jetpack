package nlab.practice.jetpack.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Android Scheduler 등의 의존성 제거를 위한 Scheduler Factory
 *
 * @author Doohyun
 */
interface SchedulerFactory {
    fun single(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}

class SchedulerFactoryImpl : SchedulerFactory {

    override fun single(): Scheduler = Schedulers.single()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}