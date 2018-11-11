package nlab.practice.jetpack.ui.databinding.callback

import androidx.databinding.Observable
import java.lang.ref.WeakReference

/**
 * [targetRef] 에 대한 의존성을 가진 OnPropertyChangedCallback
 *
 * TARGET 의 memory leak 방지를 위한 PropertyChangedCallback
 *
 * @author Doohyun
 * @since 2018. 11. 16
 */
abstract class WeakPropertyChangedCallback<TARGET>(val targetRef: WeakReference<TARGET>)
    : Observable.OnPropertyChangedCallback()