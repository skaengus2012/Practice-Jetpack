package nlab.practice.jetpack.common.di.itemview

import android.view.View
import javax.inject.Inject

/**
 * @author Doohyun
 */
@ItemViewScope
class TestItemViewUsecase @Inject constructor(view: View)