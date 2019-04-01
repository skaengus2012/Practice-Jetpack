package nlab.practice.jetpack.repository

import io.reactivex.Single

/**
 * @author Doohyun
 * @since 2019. 01. 23
 */
class ImagePoolRepository {

    private val _images = listOf("https://spark.adobe.com/images/landing/examples/blizzard-album-cover.jpg",
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

    fun getSize(): Int = _images.size

    fun get(index: Int): String? = _images[index]
}