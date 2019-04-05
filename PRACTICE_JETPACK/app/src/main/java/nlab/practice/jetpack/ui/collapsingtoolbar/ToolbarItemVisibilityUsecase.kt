package nlab.practice.jetpack.ui.collapsingtoolbar

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import io.reactivex.subjects.BehaviorSubject

/**
 * AppBarLayout 내부 Collapsing 상태에 따라, contentScrim 이 표시되는지 여부를 알려주는 기능 담당.
 *
 * @author Doohyun
 * @since 2019. 04. 05
 */
class ToolbarItemVisibilityUsecase(
        appbarLayoutProvider: () -> AppBarLayout,
        collapsingToolbarLayoutProvider: () -> CollapsingToolbarLayout) {

    private val _collapsingLayout: CollapsingToolbarLayout by lazy(collapsingToolbarLayoutProvider)

    private val _appbarLayout: AppBarLayout by lazy(appbarLayoutProvider)

    val scrimVisibilityChangeSubject = BehaviorSubject.create<Boolean>()

    fun initialize(initVisibility: Boolean) {
        scrimVisibilityChangeSubject.onNext(initVisibility)

        _appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener {
            _, offset
            ->
            onOffSetChanged(offset)
        })
    }


    private fun onOffSetChanged(offset: Int) {
        val collapsingHeight = _collapsingLayout.height
        val collapsingTriggerHeight = _collapsingLayout.scrimVisibleHeightTrigger

        val isValid = collapsingHeight != 0 && collapsingTriggerHeight != 0
        if (isValid) {
            val isShowToolbarItem = (collapsingHeight + offset < collapsingTriggerHeight)

            scrimVisibilityChangeSubject.value
                    ?.takeIf { it != isShowToolbarItem}
                    ?.run { scrimVisibilityChangeSubject.onNext(isShowToolbarItem) }
        }
    }
}