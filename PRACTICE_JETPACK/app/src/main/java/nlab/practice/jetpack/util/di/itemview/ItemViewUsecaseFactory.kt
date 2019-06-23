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