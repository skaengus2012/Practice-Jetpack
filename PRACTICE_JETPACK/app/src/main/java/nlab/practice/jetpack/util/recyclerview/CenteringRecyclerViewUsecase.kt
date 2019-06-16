package nlab.practice.jetpack.util.recyclerview

import nlab.practice.jetpack.util.lazyPublic
import org.mightyfrog.widget.CenteringRecyclerView

/**
 * @author Doohyun
 */
class CenteringRecyclerViewUsecase (viewSupplier: () -> CenteringRecyclerView) : RecyclerViewUsecase(viewSupplier) {

    private val view by lazyPublic(viewSupplier)

    fun center(index: Int)  {
        view.center(index)
    }
}