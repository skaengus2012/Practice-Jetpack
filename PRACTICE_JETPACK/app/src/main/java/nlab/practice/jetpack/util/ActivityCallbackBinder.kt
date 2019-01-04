package nlab.practice.jetpack.util

/**
 * Activity 의 Callback 에 대한 연결자 정의
 *
 * @author Doohyun
 */
class ActivityCallbackBinder {

    private var _onBackPressedCommand: (()-> Boolean)? = null

    /**
     * onBackPressed 에 실행할 [action] 정의
     *
     * 행위의 결과는 부모클래스의 onBackPressed 를 완전히 덮을 것인지 확인
     *
     * 부모클래스의 onBackPressed 실행여부
     * true -> 실행안함
     * false -> 실행
     */
    fun onBackPressed(action: () -> Boolean) {
        _onBackPressedCommand = action
    }

    fun getOnBackPressCommand(): (()-> Boolean)? = _onBackPressedCommand
}


