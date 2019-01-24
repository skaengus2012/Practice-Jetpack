package nlab.practice.jetpack.util.di.itemview

import android.view.View
import dagger.BindsInstance
import dagger.Component

/**
 * Item View 에 대한 유즈케이스를 생성할 수 있는 팩토리
 *
 * 이 곳에 View Instance 를 참조해야하는 유스케이스를 생성할 수 있도록 하자
 *
 * @author Doohyun
 */
@ItemViewScope
@Component
interface ItemViewUsecaseFactory {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setView(view: View): Builder
        fun build(): ItemViewUsecaseFactory
    }
}