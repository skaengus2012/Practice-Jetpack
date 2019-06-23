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

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import java.lang.ref.WeakReference

/**
 * @author Doohyun
 */
class OnPropertyChangedCallbackManager<TARGET, VIEW_MODEL> (
        val targetRef: WeakReference<TARGET>,
        val propertyIds: IntArray,
        val biConsumer: (TARGET, VIEW_MODEL) -> Unit) {

    private val propertyChangedCallback = WeakPropertyChangedCallbackImpl()

    private var viewModel: VIEW_MODEL? = null

    fun addCallback(viewModel: VIEW_MODEL, baseObservable: BaseObservable) {
        this.viewModel = viewModel
        baseObservable.addOnPropertyChangedCallback(propertyChangedCallback)
    }

    fun removeCallback(baseObservable: BaseObservable) {
        baseObservable.removeOnPropertyChangedCallback(propertyChangedCallback)
    }

    inner class WeakPropertyChangedCallbackImpl : Observable.OnPropertyChangedCallback() {

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            viewModel?.let {
                viewModel
                ->
                sender?.run { onTargetPropertyChanged(viewModel, propertyId) }
            }
        }

        private fun onTargetPropertyChanged(sender: VIEW_MODEL, propertyId: Int) {
            targetRef.get()
                    ?.takeIf { isValidPropertyId(propertyId) }
                    ?.run { biConsumer(this, sender) }
        }

        private fun isValidPropertyId(propertyId: Int) : Boolean =
                propertyIds.isEmpty() or (propertyId == BR._all) or (propertyId in propertyIds)
    }
}