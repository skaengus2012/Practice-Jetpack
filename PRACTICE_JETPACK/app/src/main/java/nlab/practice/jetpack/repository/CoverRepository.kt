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

package nlab.practice.jetpack.repository

import dagger.Reusable
import io.reactivex.Single
import nlab.practice.jetpack.repository.model.Cover
import javax.inject.Inject

/**
 * @author Doohyun
 */
@Reusable
class CoverRepository @Inject constructor() {

    fun getCover(): Single<Cover> = Single.fromCallable {
        Cover(
            title = "이번주 트와이스 답은\n정해져 있고 넌 좋아만 하면 돼",
            imageUrl = "http://image.chosun.com/sitedata/image/201702/24/2017022400691_0.jpg"
        )
    }

}