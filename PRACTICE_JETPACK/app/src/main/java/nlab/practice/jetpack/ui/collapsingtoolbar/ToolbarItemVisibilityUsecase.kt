package nlab.practice.jetpack.ui.collapsingtoolbar

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.util.lazyPublic

/**
 * AppBarLayout 내부 Collapsing 상태에 따라, contentScrim 이 표시되는지 여부를 알려주는 기능 담당.
 *
 * @author Doohyun
 * @since 2019. 04. 05
 */
class ToolbarItemVisibilityUsecase(
        appbarLayoutProvider: () -> AppBarLayout,
        collapsingToolbarLayoutProvider: () -> CollapsingToolbarLayout) {

    private val collapsingLayout: CollapsingToolbarLayout by lazyPublic(collapsingToolbarLayoutProvider)

    private val appbarLayout: AppBarLayout by lazyPublic(appbarLayoutProvider)

    val scrimVisibilityChangeSubject = BehaviorSubject.create<Boolean>()

    fun initialize(initVisibility: Boolean) {
        scrimVisibilityChangeSubject.onNext(initVisibility)

        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
            _, offset
            ->
            onOffSetChanged(offset)
        })
    }

    private fun onOffSetChanged(offset: Int) {
        val collapsingHeight = collapsingLayout.height
        val collapsingTriggerHeight = collapsingLayout.scrimVisibleHeightTrigger

        val isValid = collapsingHeight != 0 && collapsingTriggerHeight != 0
        if (isValid) {
            val isShowToolbarItem = (collapsingHeight + offset < collapsingTriggerHeight)

            scrimVisibilityChangeSubject.value
                    ?.takeIf { it != isShowToolbarItem}
                    ?.run { scrimVisibilityChangeSubject.onNext(isShowToolbarItem) }
        }
    }
}