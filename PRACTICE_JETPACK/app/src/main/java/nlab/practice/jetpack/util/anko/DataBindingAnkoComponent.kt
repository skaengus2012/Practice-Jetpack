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

package nlab.practice.jetpack.util.anko

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.anko.binder.*
import org.jetbrains.anko.AnkoComponent

/**
 * DataBinding 을 처리할 수 있도록 Binder 를 의존하는 AnkoComponent
 *
 * @author Doohyun
 */
abstract class DataBindingAnkoComponent<ViewModel, U> : BaseObservable(), AnkoComponent<U> {

    private var _viewModel: ViewModel? = null
    private val binders = ObservableArrayList<Binder<ViewModel>>()

    fun setViewModel(viewModel: ViewModel) : DataBindingAnkoComponent<ViewModel, U> {
        _viewModel?.run { binders.forEach { it.removeCallback() } }

        _viewModel = viewModel
        binders.forEach { it.addCallback(viewModel) }

        return this
    }

    fun <TARGET, U: BaseObservable> TARGET.binder(mapper: (ViewModel) -> U): PropertyBinder<TARGET, U> {
        val binder = PropertyBinder<TARGET, U>(this)

        PropertyMappingBindAdapter(mapper, binder).apply {
            _viewModel?.run { addCallback(this) }
            binders.add(this)
        }

        return binder
    }

    fun <TARGET> TARGET.binder(): PropertyBinder<TARGET, ViewModel> {
        return PropertyBinder<TARGET, ViewModel>(this).apply {
            _viewModel?.run { addCallback(this) }
            binders.add(this)
        }
    }

    fun <TARGET: RecyclerView, ITEM> TARGET.binderList(mapper: (ViewModel)-> List<ITEM>): ListBinder<TARGET, ITEM> {
        val binder = ListBinder<TARGET, ITEM>(this)

        ListMappingBindAdapter(mapper, binder).apply {
            _viewModel?.run { addCallback(this) }
            binders.add(this)
        }

        return binder
    }
}