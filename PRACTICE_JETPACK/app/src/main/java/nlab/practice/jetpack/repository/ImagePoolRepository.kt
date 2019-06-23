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

/**
 * @author Doohyun
 * @since 2019. 01. 23
 */
class ImagePoolRepository {

    private val images = listOf("https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjYcpib3ce6YBXVlVPkbOK7j7lK_Q7qUM8whmojc3GBbTCibvL",
            "https://cloud.netlifyusercontent.com/assets/344dbf88-fdf9-42bb-adb4-46f01eedd629/e2f9aa74-7587-4a30-b0c0-4df61d7ac308/43.jpg",
            null,
            "http://hiphople.com/files/attach/images/2531590/951/265/010/17d3f9f6dfac07da9077a2ac69025222.jpg",
            "https://t1.daumcdn.net/cfile/tistory/21266735579D869932",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0qMgPDmdWZ-2QeLX8fw4iDbZl0CneWX9EbfFDUeaJ6FQvuxhR",
            "https://newsbang.com/resources/2017/03/08/30304216649680536305147929.gif",
            "http://img1.daumcdn.net/thumb/R1024x0/?fname=http://i1.daumcdn.net/cfile255/image/25065E3A57A215D718BC5E",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0mdzx9JSHadO9Q8KIfVLpVUcj_bY0kQJJRgGtypDqB3BFd5_M"
    )

    fun getSize(): Int = images.size

    fun get(index: Int): String? = images[index]
}