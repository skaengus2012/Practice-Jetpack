package nlab.practice.jetpack.model

/**
 * View 의 가상 정보 - 테스트를 위해 제작
 *
 * @author Doohyun
 * @since 2018. 11. 09
 */
class UnitTestView {

    fun <T> display(info: T?) {
        info?.run { println(this) }
    }
}