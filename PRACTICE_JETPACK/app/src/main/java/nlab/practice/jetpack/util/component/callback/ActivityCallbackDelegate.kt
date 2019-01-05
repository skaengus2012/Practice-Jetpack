package nlab.practice.jetpack.util.component.callback

import nlab.practice.jetpack.util.component.callback.command.OnBackPressedCommand

/**
 * Activity 의 Callback 에 대한 연결자 정의
 *
 * @author Doohyun
 */
class ActivityCallbackDelegate {

    var onBackPressedCommand: OnBackPressedCommand? = null

    inline fun onBackPressed(crossinline action: () -> Boolean) {
       onBackPressedCommand = object : OnBackPressedCommand {
           override fun execute(): Boolean =  action()
       }
    }
}


