/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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