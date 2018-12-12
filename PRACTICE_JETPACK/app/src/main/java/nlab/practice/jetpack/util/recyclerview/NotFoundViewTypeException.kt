package nlab.practice.jetpack.util.recyclerview

/**
 * View Type 이 존재하지 않을 때의 예외 정의
 *
 * @author Doohyun
 */
class NotFoundViewTypeException : Exception() {
    override val message: String?
        get() = "[Error] If you create an unknown ViewType and change ViewType."
}