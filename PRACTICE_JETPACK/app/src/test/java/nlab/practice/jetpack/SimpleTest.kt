package nlab.practice.jetpack

import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Test

/**
 * @author Doohyun
 */
class SimpleTest {

    @Test
    fun testSubList() {
        val ints: List<Int> = (0..100).map { it }

        Observable.fromIterable(ints).skip(91).take(10).toList().doOnSuccess { println(it) }.subscribe()
    }

    @Test
    fun testMaybeFilter() {
        Maybe.fromCallable { 1 }.doOnComplete { println("비어있음") }.subscribe()
    }
}