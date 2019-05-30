package nlab.practice.jetpack.util

import kotlin.LazyThreadSafetyMode

/**
 * Public 모드의 Lazy 초기화 정의 (lazy 과정에서 동기화 처리를 수행하지 않음)
 *
 * [block] 을 통하여, 초기화하여 결과 처리
 *
 * @return 초기화 후 추가된 객체
 */
fun <T> lazyPublic(block: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.PUBLICATION, block)