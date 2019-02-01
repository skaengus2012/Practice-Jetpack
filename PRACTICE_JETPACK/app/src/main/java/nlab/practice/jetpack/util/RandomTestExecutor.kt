package nlab.practice.jetpack.util

import android.os.SystemClock
import java.lang.RuntimeException
import kotlin.random.Random

/**
 * @author Doohyun
 * @since 2019. 02. 01
 */
object RandomTestExecutor {

    fun error(percent: Int = 50) =  Random.nextInt(100).takeIf { it <= percent }?.run {
        throw RuntimeException()
    }

    fun delay(minTime: Long = 100, maxTime: Long = 1000) = Random.nextLong(minTime, maxTime + 1).run {
        SystemClock.sleep(this)
    }
}