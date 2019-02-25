package nlab.practice.jetpack.util.di.itemview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import dagger.BindsInstance
import dagger.Component
import nlab.practice.jetpack.util.recyclerview.touch.ItemViewTouchHelperUsecaseFactory

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

        @BindsInstance
        fun setViewHoler(viewHolder: RecyclerView.ViewHolder): Builder

        fun build(): ItemViewUsecaseFactory
    }

    fun itemViewTouchHelperUsecaseFactory(): ItemViewTouchHelperUsecaseFactory
}