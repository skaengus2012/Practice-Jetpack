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

package nlab.practice.jetpack.paging

import io.reactivex.Observable
import io.reactivex.Single
import nlab.practice.jetpack.model.NonePageableItem
import nlab.practice.jetpack.model.NonePageableItemRs
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalPagingManager
import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs
import nlab.practice.jetpack.util.recyclerview.paging.positional.UnboundedPositionalPagingManager

/**
 * Test 를 위해 구현한 레파지토리
 *
 * @author Doohyun
 */
const val DATA_LOAD_DELAY_SECOND = 2000L

class TestRepository :
        CountablePositionalPagingManager.DataRepository<NonePageableItem>,
        UnboundedPositionalPagingManager.DataRepository<NonePageableItem> {
    val items = ArrayList<NonePageableItem>()

    init {
        (0 until 1000).map { NonePageableItem() }.let { items.addAll(it) }
    }

    override fun getTotalCount(): Single<Int> = Single.just(items.size)

    override fun getCountablePositionalRs(offset: Int, limit: Int): Single<CountablePositionalRs<NonePageableItem>> {
        Thread.sleep(DATA_LOAD_DELAY_SECOND)

        return Observable.fromIterable(items)
                .take(limit.toLong())
                .skip(offset.toLong())
                .toList()
                .map { NonePageableItemRs(items.size, it) }
    }

    override fun getItems(offset: Int, limit: Int): Single<List<NonePageableItem>> {
        Thread.sleep(DATA_LOAD_DELAY_SECOND)

        return Observable.fromIterable(items)
                .take(limit.toLong())
                .skip(offset.toLong())
                .toList()
    }
}