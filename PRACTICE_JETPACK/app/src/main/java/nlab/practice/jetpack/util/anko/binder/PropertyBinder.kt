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

package nlab.practice.jetpack.util.anko.binder

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import java.lang.ref.WeakReference

private typealias PCallback<T, OBS> = PropertyBinder.DefaultActionOnPropertyChangedCallback<T, OBS>

/**
 * BaseObservable 의 Property 를 Support 하는 Binder
 *
 * @author Doohyun
 */
class PropertyBinder<T, OBS>(private val target: T) : Binder<OBS> {

    private var observable: OBS? = null
    private val onPropertyChangedCallbacks = ArrayList<PCallback<T, OBS>>()

    /**
     * [any] 에 내부 생성된 Callback 을 추가한다.
     * [any] 가 BaseObservable 이 아니라면, Callback 추가를 하지 않고, drive 에서 정의된 기능을 수행한다.
     *
     * @param any 리스너를 추가할 대상
     */
    override fun addCallback(any: OBS) {
        observable = any
        onPropertyChangedCallbacks.forEach { addCallback(any, it) }
    }

    private fun addCallback(observable: OBS, callback: PCallback<T, OBS>) {
        callback.changeCallback(target, observable)
        observable.toObservable()?.run { addOnPropertyChangedCallback(callback) }
    }

    /**
     * [observable] 에 추가된 모든 Callback 을 삭제한다.
     */
    override fun removeCallback() {
        onPropertyChangedCallbacks.forEach { observable?.toObservable()?.removeOnPropertyChangedCallback(it) }
    }

    /**
     * [observable] 알림 처리
     */
    override fun notifyChanged() {
        observable?.toObservable()?.notifyChange()
    }

    /**
     * 알림으로 등장한 [checkPropertyId] 가 유효한가?
     *
     * 조건
     *  [checkPropertyId] 가 [validPropertyIds] 에 속해있음
     *  [checkPropertyId] 가 all 임
     *
     * @param validPropertyIds   리스너에서 서포트하는 목록
     * @param checkPropertyId    업데이트 항목
     * @return              유효 여부
     */
    private fun isValidPropertyId(validPropertyIds: IntArray, checkPropertyId: Int) : Boolean = when(checkPropertyId) {
        BR._all -> true
        in validPropertyIds -> true
        else -> false
    }

    private fun OBS.toObservable(): BaseObservable? = this as? BaseObservable

    /**
     * [propertyIds] 에 속한 요청에 대해 업데이트 시, [changeCallback] 을 처리하는 리스너를 추가한다.
     *
     * 상세 조건은 PropertyBinder::isValidPropertyId 에 따른다.
     */
    fun drive(vararg propertyIds: Int, changeCallback: T.(OBS) -> Unit): T {
        val weakRef  = WeakReference(target)

        object: PCallback<T, OBS>(changeCallback) {

            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                observable?.let {
                    if (isValidPropertyId(propertyIds, propertyId)) {
                        weakRef.get()?.let { target -> changeCallback(target, it)}
                    }
                }
            }

        }.let {
            callback
            ->
            onPropertyChangedCallbacks.add(callback)
            observable?.run { addCallback(this, callback) }
        }

        return target
    }

    abstract class DefaultActionOnPropertyChangedCallback<T, OBS>(val changeCallback: T.(OBS) -> Unit):
            Observable.OnPropertyChangedCallback()
}