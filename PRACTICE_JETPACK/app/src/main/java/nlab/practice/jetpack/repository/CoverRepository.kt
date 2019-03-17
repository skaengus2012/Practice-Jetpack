package nlab.practice.jetpack.repository

import io.reactivex.Single
import nlab.practice.jetpack.repository.model.Cover

/**
 * @author Doohyun
 */
class CoverRepository {

    fun getCover(): Single<Cover> = Single.fromCallable {
        Cover(
                title = "이번주 트와이스 답은\n정해져 있고 넌 좋아만 하면 돼",
                imageUrl = "http://image.chosun.com/sitedata/image/201702/24/2017022400691_0.jpg")
    }

}