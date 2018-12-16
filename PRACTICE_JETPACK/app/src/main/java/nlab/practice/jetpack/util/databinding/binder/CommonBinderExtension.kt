package nlab.practice.jetpack.util.databinding.binder

import android.view.View

/**
 * Binder 클래스의 확장체
 *
 * 데이터바인딩의 BindingAdapter 역할을 할 수 있음
 *
 * @author Doohyun
 */


/**
 * onClick 에 대한 리스너 연결을 수행한다.
 */
inline fun <T: View, OBS> PropertyBinder<T, OBS>.onClick(crossinline onClickBehavior: View.(OBS)-> Unit) : T = drive {
    observable
    ->
    setOnClickListener {
        onClickBehavior(it, observable)
    }
}