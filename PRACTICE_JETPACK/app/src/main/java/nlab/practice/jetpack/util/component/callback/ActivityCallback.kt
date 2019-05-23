package nlab.practice.jetpack.util.component.callback

/**
 * Activity 의 Callback 에 대한 연결자 정의
 *
 * @author Doohyun
 */
class ActivityCallback {

    var onBackPressedCommand: (()-> Boolean)? = null
    private set

    fun onBackPressed(action: () -> Boolean) {
       onBackPressedCommand = action
    }
}


