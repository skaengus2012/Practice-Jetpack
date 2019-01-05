package nlab.practice.jetpack.util.component.callback.command

/**
 * OnBackPressed 의 발생에 대한 커맨드
 */
interface OnBackPressedCommand {

    /**
     * OnBackPressed 를 실행 하고, 부모메소드를 실행할지 결정한다.
     *
     * @return true: 실행안함, false: 실행
     */
    fun execute(): Boolean
}