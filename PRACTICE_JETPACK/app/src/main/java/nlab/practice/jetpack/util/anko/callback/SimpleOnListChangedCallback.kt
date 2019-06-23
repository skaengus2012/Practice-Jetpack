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

package nlab.practice.jetpack.util.anko.callback

import androidx.databinding.ObservableList

/**
 * RecyclerView 의 어떤 변화에도 단순하게 onChange 가 등장하도록 처리된 콜백
 *
 * @author Doohyun
 * @since 2018. 11. 16
 */
abstract class SimpleOnListChangedCallback<ITEM> : ObservableList.OnListChangedCallback<ObservableList<ITEM>>() {

    override fun onItemRangeRemoved(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
       onChanged(sender)
    }

    override fun onItemRangeMoved(sender: ObservableList<ITEM>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeInserted(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }

    override fun onItemRangeChanged(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
        onChanged(sender)
    }
}