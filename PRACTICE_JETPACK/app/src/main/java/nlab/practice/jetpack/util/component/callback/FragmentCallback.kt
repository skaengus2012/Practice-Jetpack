package nlab.practice.jetpack.util.component.callback

/**
 * Fragment 의 Callback 에 대한 연결자 정의
 *
 * @author Doohyun
 */
class FragmentCallback {
    var onBackPressedCommand: (()-> Boolean)? = null

    inline fun onBackPressed(crossinline action: () -> Boolean) {
        onBackPressedCommand = { action() }
    }
}