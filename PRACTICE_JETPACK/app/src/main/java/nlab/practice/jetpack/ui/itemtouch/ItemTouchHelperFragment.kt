package nlab.practice.jetpack.ui.itemtouch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import dagger.Provides
import nlab.practice.jetpack.databinding.FragmentItemTouchHelperBinding
import nlab.practice.jetpack.util.di.fragment.FragmentScope
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.touch.SwipeDeleteTouchEventHelperCallback
import nlab.practice.jetpack.util.recyclerview.touch.VerticalDragItemTouchHelperCallback
import javax.inject.Inject
import javax.inject.Named

/**
 * Drag N Drop 을 수행하는 화면 정의
 *
 * @author Doohyun
 */
class ItemTouchHelperFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: ItemTouchHelperViewModel

    lateinit var binding: FragmentItemTouchHelperBinding

    override fun onCreateBindingView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentItemTouchHelperBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

    @dagger.Module
    class Module {
        @FragmentScope
        @Provides
        fun provideItemFactory(@Named(NAME_VERTICAL_DRAG) itemTouchHelper: ItemTouchHelper):
                ItemTouchHelperItemViewModelFactory = ItemTouchHelperItemViewModelFactory { itemTouchHelper }

        @FragmentScope
        @Provides
        fun provideDragTouchHelperCallback(): VerticalDragItemTouchHelperCallback
                = VerticalDragItemTouchHelperCallback(true)

        @FragmentScope
        @Provides
        @Named(NAME_VERTICAL_DRAG)
        fun provideDragItemTouchHelper(dragItemTouchHelper: VerticalDragItemTouchHelperCallback): ItemTouchHelper
                = ItemTouchHelper(dragItemTouchHelper)

        @FragmentScope
        @Provides
        fun provideSwipeDeleteTouchHelperCallback(): SwipeDeleteTouchEventHelperCallback
                = SwipeDeleteTouchEventHelperCallback(ItemTouchHelper.START)

        @FragmentScope
        @Provides
        @Named(NAME_SWIPE_DELETE)
        fun provideSwipeDeleteItemTouchHelper(swipeItemTouchHelper: SwipeDeleteTouchEventHelperCallback) : ItemTouchHelper
                = ItemTouchHelper(swipeItemTouchHelper)

        /**
         * NOTE. Multi-binding 사용 못함
         * Named Tag 가 붙으면, IntoSet, IntoMap 사용불가
         *
         * [dragHelper] 와 [swipeHelper] 를 Named 로 받아, Multi-binding 처럼 출력 처리
         * -> 이를 통해, 모든 사용처에서는 Set 만 바라보고 처리할 수 있도록 함
         */
        @FragmentScope
        @Provides
        fun provideItemTouchHelpers(
                @Named(NAME_VERTICAL_DRAG) dragHelper: ItemTouchHelper,
                @Named(NAME_SWIPE_DELETE) swipeHelper: ItemTouchHelper): Set<ItemTouchHelper> {
            return setOf(dragHelper, swipeHelper)
        }
    }

    /**
     * ItemTouchHelper 멀티 바인딩을 위한 키셋 정의
     */
    companion object  {
        private const val NAME_VERTICAL_DRAG = "NAME_VERTICAL_DRAG"
        private const val NAME_SWIPE_DELETE = "NAME_SWIPE_DELETE"
    }
}