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

package nlab.practice.jetpack.model

import nlab.practice.jetpack.util.recyclerview.paging.positional.CountablePositionalRs

/**
 * @author Doohyun
 * @since 2019. 01. 15
 */
class NonePageableItemRs(private val totalCount: Int, private val items: List<NonePageableItem>) :
        CountablePositionalRs<NonePageableItem> {
    override fun getTotalCount(): Int = totalCount
    override fun getItems(): List<NonePageableItem> = items
}