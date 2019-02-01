package nlab.practice.jetpack.util

import java.lang.RuntimeException
import kotlin.random.Random

/**
 * @author Doohyun
 * @since 2019. 02. 01
 */
object RandomErrorExecutor {

    fun execute(percent: Int = 50) =  Random.nextInt(100).takeIf { it <= percent }?.run {
        throw RuntimeException()
    }

}